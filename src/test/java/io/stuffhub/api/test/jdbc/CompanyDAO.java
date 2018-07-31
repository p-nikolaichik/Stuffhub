package io.stuffhub.api.test.jdbc;

import java.math.BigInteger;
import java.util.List;

public interface CompanyDAO {

    List<Candidate> getAllCandidate();
    Candidate getCandidateSkills(int id);
}
