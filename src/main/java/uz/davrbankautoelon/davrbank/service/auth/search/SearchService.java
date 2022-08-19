package uz.davrbankautoelon.davrbank.service.auth.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.davrbankautoelon.davrbank.controller.image.ImageController;
import uz.davrbankautoelon.davrbank.dto.auth.SearchDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralImagesDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralViewDto;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchService {

    private final JdbcTemplate jdbcTemplate;
    private final ImageController controller;

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schema;

    public SearchService(JdbcTemplate jdbcTemplate, ImageController controller) {
        this.jdbcTemplate = jdbcTemplate;
        this.controller = controller;
    }


    public List<MoralViewDto> searchMoral(SearchDto dto){
        log.info("call search for moral");

        String sql = "select * from " + schema + "._moral where 1=1 ";
        String branch = "and sub_branch = '"+dto.getBranch()+"'";
        String status = "and status = '"+dto.getStatus()+"'";
        String inn = "and inn = '"+dto.getName()+"'";

        if(!dto.getStatus().equals("")){
            sql += status;
        }
        if(!dto.getBranch().equals("")){
            sql += branch;
        }
        if(!dto.getName().equals("")){
            sql += inn;
        }
        if(dto.getName().equals("") && dto.getBranch().equals("") && dto.getStatus().equals("")){
            return new ArrayList<>();
        }
        log.info(sql);
        List<MoralViewDto> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MoralViewDto.class));
        List<MoralViewDto> collect = results.stream().peek(p -> {
                    String getImages = "select * from " + schema + ".moral_image where moral_image_id = '" + p.getId() + "'";
                    log.info(getImages);
                    List<MoralImagesDto> query = jdbcTemplate.query(getImages, new BeanPropertyRowMapper<>(MoralImagesDto.class));
                    List<MoralImagesDto> collect1 = query.stream().peek(img -> {
                        try {
                            img.setCertificate(controller.downloadFile(img.getCertificate()));
                        } catch (Exception e) {
                            log.info(e.getMessage());
                        }
                        String getSecondaryImage = "select * from " + schema + ".secondary_image where image_secondary_id = '" + img.getId() + "'";
                        List<MoralImagesDto.SecondaryImage> query1 = jdbcTemplate.query(getSecondaryImage, new BeanPropertyRowMapper<>(MoralImagesDto.SecondaryImage.class));
                        img.setDocument1(query1);
                    }).collect(Collectors.toList());
                    p.setImages(collect1);
                })
                .sorted(Comparator.comparing(MoralViewDto::getId).reversed())
                .collect(Collectors.toList());
        return collect;

    }

    public List<PhysicalViewDto> searchPhysical(SearchDto dto) {

        log.info("call search for physical");

        String query = "select * from "+schema+".auto_elon_physical s where  1 = 1";

        String status = "and  s.status = '" + dto.getStatus() + "' ";

        String date = " and (s.created_date between '" + dto.getDate() + "' and '" + dto.getDate() + " 23:59:59')";

        String name = " and (s.passport_number like '%" + dto.getName() + "%' or s.phone_number like '%" + dto.getName() + "%')";

        String branch = "and  s.sub_branch = '" + dto.getBranch() + "'";


        if (!dto.getBranch().equals("")) {
            query += branch;
        }
        if (!dto.getName().equals("")) {
            query += name;
        }
        if (!dto.getDate().equals("")) {
            query += date;
        }
        if (!dto.getStatus().equals("")) {
            query += status;
        }
        query += " ORDER BY id desc ";
        if (dto.getName().equals("") && dto.getStatus().equals("") && dto.getDate().equals("") && dto.getBranch().equals("")) {
            return null;
        }
        log.info("Fetching  request by sql {}", query);
        System.out.println(query);
       return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PhysicalViewDto.class))
                .stream().peek(p -> {
            try {
                p.setImage_path(controller.downloadFile(p.getImage_path()));
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }).collect(Collectors.toList());


    }
}
