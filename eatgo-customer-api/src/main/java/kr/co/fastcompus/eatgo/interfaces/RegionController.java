package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.RegionService;
import kr.co.fastcompus.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list(){

        List<Region> regions = regionService.getResions();
        return regions;
    }

}
