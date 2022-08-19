package uz.davrbankautoelon.davrbank.repository.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;

import java.util.List;

@Repository
@Slf4j
public class KIARepository {

    private final JdbcTemplate jdbcTemplate;

    public KIARepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<PhysicalViewDto> getList(){
        String query = "select * from auto_elon_physical s where s.status = 'V'";
        log.info(query);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PhysicalViewDto.class));
    }
}
