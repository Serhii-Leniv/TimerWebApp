package nordcode.Focus_Timer.repository;

import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.model.StatusEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SessionRepository{

    private final JdbcTemplate jdbcTemplate;

    public SessionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(FocusSession session){
        String sql = "INSERT INTO focus_sessions (task_name, start_time, status) VALUES (?,?,?)";
        jdbcTemplate.update(sql,
                session.getTaskName(),
                session.getStartTime().toString(),
                session.getStatus().name()
        );
    }

    public FocusSession findById(Long id){
        String sql = "SELECT * FROM focus_session WHERE id = ?";
        List<FocusSession> sessions = jdbcTemplate.query(sql, sessionRowMapper, id);
        if(sessions.isEmpty()){
            return null;
        }else {
            return sessions.get(0);
        }

    }

    public void deleteSessionById(Long id){
        String sql = "DELETE FROM focus_sessions WHERE id = ? ";
        jdbcTemplate.update(sql,id);
    }

    public void updateEndTime(Long id, LocalDateTime endTime, Long duration, StatusEnum statusEnum){
        String sql = "UPDATE focus_sessions SET end_time = ?, duration_seconds = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                endTime.toString(),
                duration,
                statusEnum.name(),
                id
        );
    }

    public List<FocusSession> findALL(){
        String sql = "SELECT * FROM focus_sessions ORDER by id DESC";
        return  jdbcTemplate.query(sql,sessionRowMapper);
    }

    private final RowMapper<FocusSession> sessionRowMapper = (rs, rowNum) -> {
        FocusSession session = new FocusSession();

        session.setId(rs.getLong("id"));
        session.setTaskName(rs.getString("task_name"));

        String startTimeStr = rs.getString("start_time");
        if (startTimeStr != null) {
            session.setStartTime(LocalDateTime.parse(startTimeStr));
        }

        String endTimeStr = rs.getString("end_time");
        if (endTimeStr != null) {
            session.setEndTime(LocalDateTime.parse(endTimeStr));
        }

        session.setDurationSeconds(rs.getLong("duration_seconds"));

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            session.setStatus(StatusEnum.valueOf(statusStr));
        }
        return session;
    };
}

