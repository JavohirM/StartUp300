package uz.davrbankautoelon.davrbank.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.davrbankautoelon.davrbank.controller.image.ImageController;
import uz.davrbankautoelon.davrbank.dto.auth.physical.ImageDto;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;
import uz.davrbankautoelon.davrbank.dto.auth.SearchDto;


import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
@PersistenceContext
public class PhysicalRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ImageController controller;

    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schema;

    public PhysicalRepository(JdbcTemplate jdbcTemplate, ImageController controller) {
        this.jdbcTemplate = jdbcTemplate;
        this.controller = controller;
    }

    public PhysicalViewDto getRequest(String requestId) {
        try {
            String sql = "select * from "+schema+".auto_elon_physical  where id='" + requestId + "'";
            log.info("Fetching get request by sql {}", sql);
            PhysicalViewDto physicalViewDto = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
            assert physicalViewDto != null;
            physicalViewDto.setImage_path(controller.downloadFile(physicalViewDto.getImage_path()));
            return physicalViewDto;
        } catch (Exception e) {
            return null;
        }
    }

    public List<PhysicalViewDto> getByBranch(String branch) {

        try {
            String sql = "select * from auto_elon_physical  where sub_branch='" + branch + "'";
            log.info("Fetching all requests by sql {}", sql);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<PhysicalViewDto> searchByBranch(SearchDto dto) {
        try {
            String query = "select * from auto_elon_physical s  where ";

            String branch = " s.sub_branch='" + dto.getBranch() + "'";

            String status = " s.status = '" + dto.getStatus() + "' and  ";

            String date = " (s.created_date between '" + dto.getDate() + "' and '" + dto.getDate() + " 23:59:59') and ";

            String name = " (s.passport_number like '%" + dto.getName() + "%' or s.phone_number like '%" + dto.getName() + "%') and";

            if (!dto.getName().equals("")) {
                query += name;
            }
            if (!dto.getDate().equals("")) {
                query += date;
            }
            if (!dto.getStatus().equals("")) {
                query += status;
            }


            String sql = query + branch;
             sql += " ORDER BY id desc ";
            log.info("Fetching search by sql {}", sql);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<ImageDto> getImages(String requestId) {
        try {
            String sql = "select * from image  where "+schema+".auto_elon_physical_id='" + requestId + "'";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ImageDto.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<PhysicalViewDto> getAll() {
        try {
            String sql = "select * from "+schema+".auto_elon_physical where status = 'N' order by id desc ";
            log.info("Fetching all requests by sql {}", sql);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<PhysicalViewDto> getAllByBranch(String branch) {
        try {
            String sql = "select * from "+schema+".auto_elon_physical where status = 'N' and sub_branch = '"+branch+"' order by id desc ";
            log.info("Fetching all requests by sql {}", sql);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
        } catch (Exception e) {
            return null;
        }
    }

    public void valid(String requestId) {
        try {
            String sql = "update auto_elon_physical set status = 'V'  where id = '" + requestId + "'";
            log.info("Fetching  request by sql {}", sql);
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.out.println("asd");
        }
    }

    public void reject(String requestId, String message) {
        try {
            String sql = "update auto_elon_physical set status = 'A', reject_message='"+message+"'  where id = '" + requestId + "'";
            log.info("Fetching  request by sql {}", sql);
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.out.println("Error in rejection");
        }
    }


    public List<PhysicalViewDto> search(SearchDto dto) {


        String query = "select * from auto_elon_physical s where  1 = 1 ";

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
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
    }
}
