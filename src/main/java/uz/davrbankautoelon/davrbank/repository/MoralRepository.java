package uz.davrbankautoelon.davrbank.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.davrbankautoelon.davrbank.controller.image.ImageController;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralImagesDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.MoralViewDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class MoralRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ImageController controller;
    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schema;


    public MoralRepository(JdbcTemplate jdbcTemplate, ImageController controller) {
        this.jdbcTemplate = jdbcTemplate;
        this.controller = controller;
    }

    public List<MoralViewDto> getAll(String branch) {
        try {
            log.info(branch);
            String sql;
            if(branch != null) {
               sql = "select * from " + schema + "._moral where status = 'N' and sub_branch= '"+branch+"'";
            }
            else {
                sql = "select * from " + schema + "._moral where status = 'N'";
            }
            log.info(sql);
            List<MoralViewDto> images = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MoralViewDto.class));
            List<MoralViewDto> collect = images.stream().peek(p -> {
                        String getImages = "select * from " + schema + ".moral_image where moral_image_id = '" + p.getId() + "'";
                        log.info(getImages);
                        List<MoralImagesDto> query = jdbcTemplate.query(getImages, new BeanPropertyRowMapper<>(MoralImagesDto.class));
                        List<MoralImagesDto> collect1 = query.stream().peek(img -> {
                            String getSecondaryImage = "select * from " + schema + ".secondary_image where image_secondary_id = '" + img.getId() + "'";
                            List<MoralImagesDto.SecondaryImage> query1 = jdbcTemplate.query(getSecondaryImage, new BeanPropertyRowMapper<>(MoralImagesDto.SecondaryImage.class));
                            img.setDocument1(query1);
                            //to Base64
                            try {
                                img.setCertificate(controller.downloadFile(img.getCertificate()));

                            } catch (Exception e) {
                                log.info(e.getMessage());
                            }
                        }).collect(Collectors.toList());
                        p.setImages(collect1);
                    })
                    .sorted(Comparator.comparing(MoralViewDto::getId).reversed())
                    .collect(Collectors.toList());
            return collect;
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return null;
        }
    }


    public MoralViewDto getById(String id) {
        try {

            String sql = "select * from " + schema + "._moral where id = '" + id + "'";

            MoralViewDto images = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MoralViewDto.class));
            String getImages = "select * from " + schema + ".moral_image where moral_image_id = '" + images.getId() + "'";
            log.info(getImages);
            List<MoralImagesDto> query = jdbcTemplate.query(getImages, new BeanPropertyRowMapper<>(MoralImagesDto.class));
            List<MoralImagesDto> collect1 = query.stream().peek(img -> {
                String getSecondaryImage = "select * from " + schema + ".secondary_image where image_secondary_id = '" + img.getId() + "'";
                List<MoralImagesDto.SecondaryImage> query1 = jdbcTemplate.query(getSecondaryImage, new BeanPropertyRowMapper<>(MoralImagesDto.SecondaryImage.class));
                List<MoralImagesDto.SecondaryImage> collect2 = query1.stream().peek(secondaryImage -> {
                    try {
                        secondaryImage.setImagePath(controller.downloadFile(secondaryImage.getImagePath()));
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                }).collect(Collectors.toList());
                img.setDocument1(collect2);

                //to Base64
                try {
                    img.setPassport(controller.downloadFile(img.getPassport()));
                    img.setFile(controller.downloadFile(img.getFile()));
                    img.setDocument2(controller.downloadFile(img.getDocument2()));
                    img.setCertificate(controller.downloadFile(img.getCertificate()));

                } catch (Exception e) {
                    log.info(e.getMessage());
                }

            }).collect(Collectors.toList());
            images.setImages(collect1);
            return images;
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return null;
        }

    }


}
