package com.portafolio.my_portafolio_backend.repository;

import com.portafolio.my_portafolio_backend.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor//cuando lo metemos por lombok el lo hace automatico, hay que recordar que siempre que sea una variable final debe estar implementada en el constructor
public class SkillRepositoryImpl implements ISkillRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Skill>skillRowMapper=(rs, rowNum) ->{
        Skill skill=new Skill();
        skill.setId(rs.getLong("id"));
        skill.setName(rs.getString("name"));
        skill.setLevelPercentage(rs.getInt("level_percentage"));
        skill.setIconClass(rs.getString("icon_class"));
        skill.setPersonalInfoId(rs.getLong("personal_info_id"));
        return skill;
    };//como es una expresion lamda ;


    @Override
    public Skill save(Skill skill) {

        if(skill.getId()==null){
            String sql = "INSERT INTO skills (name, level_percentage, icon_class, personal_info_id) VALUES (?, ?, ?, ?)";
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String []{"id"});
                ps.setString(1, skill.getName());
                ps.setInt(2, skill.getLevelPercentage());
                ps.setString(3, skill.getIconClass());
                ps.setLong(4, skill.getPersonalInfoId());
                return ps;
            },keyholder);
            skill.setId(Objects.requireNonNull(keyholder.getKey()).longValue());
        }else{
            String sql = "UPDATE skills SET name = ?, level_percentage = ?, icon_class = ?, " +
                    "personal_info_id = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    skill.getName(),
                    skill.getLevelPercentage(),
                    skill.getIconClass(),
                    skill.getPersonalInfoId(),
                    skill.getId());
        }
        return skill;
    }

    @Override
    public Optional<Skill> findById(Long id) {
        String sql = "SELECT id, name, level_percentage, icon_class, personal_info_id FROM skills WHERE id = ?";
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, skillRowMapper, id));//queryForObject busca un objeto especifico por eso se le pone el catch

        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Skill> finAll() {
        String sql="SELECT * FROM skills";
        return jdbcTemplate.query(sql,skillRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql="DELETE FROM skills WHERE id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public List<Skill> findByPersonalInfoId(Long personalInfoId) {
        String sql = "SELECT id, name, level_percentage, icon_class, personal_info_id FROM skills " +
                "WHERE personal_info_id = ?";
        return jdbcTemplate.query(sql, skillRowMapper, personalInfoId);//van 3 en orden, sql, rowMapper y la variable del ?
    }
}
