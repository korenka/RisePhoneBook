package com.koren.phonebook_api.repository;

import com.koren.phonebook_api.model.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Contact> rowMapper = (rs, rowNum) -> {
        Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setPhone(rs.getString("phone"));
        contact.setAddress(rs.getString("address"));
        return contact;
    };

    public List<Contact> findAll(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM contacts LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{size, offset}, rowMapper);
    }

    public Optional<Contact> findById(Long id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        List<Contact> contacts = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
        if (contacts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(contacts.get(0));
        }
    }

    public Contact save(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, phone, address) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getAddress());
            return ps;
        }, keyHolder);

        contact.setId(keyHolder.getKey().longValue());
        return contact;
    }

    public int update(Long id, Contact contact) {
        String sql = "UPDATE contacts SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE id = ?";
        return jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getPhone(), contact.getAddress(), id);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
