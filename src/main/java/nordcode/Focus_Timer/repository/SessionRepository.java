package nordcode.Focus_Timer.repository;

import nordcode.Focus_Timer.model.FocusSession;
import nordcode.Focus_Timer.model.StatusEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkIfSessionRunning() {
        String sql = "SELECT count(*) FROM focus_sessions WHERE status = 'IN_PROGRESS'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        return count != null && count > 0;
    }

    public void save(FocusSession session) {
        System.out.println("Зберігаємо об'єкт: " + session);
        System.out.println("Ім'я завдання (getTaskName): " + session.getTaskName());

        String sql = "INSERT INTO focus_sessions (task_name, start_time, status, target_time) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, session.getTaskName());
            ps.setString(2, session.getStartTime().toString());
            ps.setString(3, session.getStatus().name());
            ps.setString(4, session.getTargetTime().toString());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            session.setId(keyHolder.getKey().longValue());
        }
    }

    public FocusSession findById(Long id) {
        String sql = "SELECT * FROM focus_sessions WHERE id = ?";
        List<FocusSession> sessions = jdbcTemplate.query(sql, sessionRowMapper, id);
        if (sessions.isEmpty()) {
            return null;
        } else {
            return sessions.getFirst();
        }
    }

    public void deleteSessionById(Long id) {
        String sql = "DELETE FROM focus_sessions WHERE id = ? ";
        jdbcTemplate.update(sql, id);
    }

    public void updateTimeAndStatus(Long id, LocalDateTime endTime, Long duration, StatusEnum statusEnum) {
        String sql = "UPDATE focus_sessions SET end_time = ?, duration_seconds = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                endTime.toString(),
                duration,
                statusEnum.name(),
                id
        );
    }

    public List<FocusSession> findALL() {
        String sql = "SELECT * FROM focus_sessions ORDER by id DESC";
        return jdbcTemplate.query(sql, sessionRowMapper);
    }

    private final RowMapper<FocusSession> sessionRowMapper = (rs, _) -> {
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

        session.setTargetTime(rs.getLong("target_time"));

        return session;
    };
}

