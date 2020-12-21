package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.Region;
import kr.co.fastcompus.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RegionServiceTests {


    @InjectMocks
    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 리전 목록
     */
    @Test
    public void list(){
        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("Seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> getRegions = regionService.getResions();
        assertThat(getRegions.get(0).getName(), is("Seoul"));

    }

    /**
     * 리전 등록
     */
    @Test
    public void create(){
        Region mockRegion = Region.builder().name("Seoul").build();

        given(regionRepository.save(any())).will(invocation -> {
            Region savedRegion = invocation.getArgument(0);
            savedRegion.setId(1234L);
            return savedRegion;
        });

        Region region = regionService.addRegion(mockRegion);
        assertThat(region.getId(), is(1234L));

        verify(regionRepository).save(any());
    }
}