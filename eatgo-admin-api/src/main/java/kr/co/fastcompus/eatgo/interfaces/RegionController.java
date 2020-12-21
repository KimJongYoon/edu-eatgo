package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.RegionService;
import kr.co.fastcompus.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/regions")
    public ResponseEntity<?> create( @RequestBody @Valid Region region) throws URISyntaxException {
        regionService.addRegion(region);
        URI location = new URI("/regions/" + region.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
