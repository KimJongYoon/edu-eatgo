package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.Region;
import kr.co.fastcompus.eatgo.domain.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getResions() {
        // TODO 올바르게 구현이 필요함.

        List<Region> regions = regionRepository.findAll();

        return regions;
    }

    public Region addRegion(Region region) {
        return regionRepository.save(region);
    }
}
