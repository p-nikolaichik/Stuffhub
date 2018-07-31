package io.stuffhub.api.test.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service("companyDAO")
public class CompanyDAOImpl implements CompanyDAO{

    @Autowired
    private JdbcTemplate template;

    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Candidate> getAllCandidate() {
        return template.query("select candidate_id, first_name, last_name from candidate", (rs, rowNumber) -> {
            Candidate candidate = new Candidate();
            candidate.setCandidate_id(rs.getInt(1));
            candidate.setFirst_name(rs.getString(2));
            candidate.setLast_name(rs.getString(3));
            return candidate;
        });
    }

    @Override
    public Candidate getCandidateSkills(int id) {
        Candidate candidate = new Candidate();
        List<String> list = template.query("select name from candidate_skill where candidate_id="+id, (rs, rowNumber) -> rs.getString(1));
        list.forEach(l -> candidate.addSkills(l));
        return candidate;
    }
}
