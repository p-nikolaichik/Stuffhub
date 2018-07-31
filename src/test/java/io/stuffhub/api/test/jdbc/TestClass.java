package io.stuffhub.api.test.jdbc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigInteger;
import java.util.List;

public class TestClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceClass.class);
        CompanyDAO dao = ctx.getBean("companyDAO", CompanyDAOImpl.class);
        ctx.close();
        List<Candidate> list = dao.getAllCandidate();
        for (Candidate candidate : list) {
            System.out.println(candidate);
        }
        Candidate candidate = dao.getCandidateSkills(1);
        candidate.getCandidateSkills();
    }
}
