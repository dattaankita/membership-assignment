package com.firstclub.membership.service;

import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.repository.MembershipTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipTierServiceImpl implements MembershipTierService {

    @Autowired
    MembershipTierRepository repository;

    @Override
    public List<MembershipTier> getAllTiers() {
        return repository.findAll();
    }
}