package nl.rabobank.data.mapper.implementation;

import lombok.RequiredArgsConstructor;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.config.CustomMapper;
import nl.rabobank.data.mapper.MongoAccountMapper;
import nl.rabobank.data.mapper.MongoPowerOfAttorneyMapper;
import nl.rabobank.mongo.document.account.MongoAccount;
import nl.rabobank.mongo.document.authorizations.MongoPowerOfAttorney;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MongoPowerOfAttorneyMapperImpl implements MongoPowerOfAttorneyMapper {


    private final CustomMapper customMapper;

    private final MongoAccountMapper mongoAccountMapper;

    @Override
    public PowerOfAttorney map(MongoPowerOfAttorney mongoPowerOfAttorney) {
        return customMapper.map(mongoPowerOfAttorney,PowerOfAttorney.PowerOfAttorneyBuilder.class)
                .account(mongoAccountMapper.map(mongoPowerOfAttorney.getAccount()))
                .build();
    }

    @Override
    public MongoPowerOfAttorney map(PowerOfAttorney powerOfAttorney, MongoAccount mongoAccount) {
        return customMapper.map(powerOfAttorney,MongoPowerOfAttorney.MongoPowerOfAttorneyBuilder.class)
                .account(mongoAccount).build();
    }
}