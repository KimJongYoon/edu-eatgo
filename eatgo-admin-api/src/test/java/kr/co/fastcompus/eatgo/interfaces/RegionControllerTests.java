package kr.co.fastcompus.eatgo.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcompus.eatgo.application.RegionService;
import kr.co.fastcompus.eatgo.domain.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
public class RegionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionService regionService;

    /**
     * 리전 목록 테스트
     * */
    @Test
    public void list() throws Exception {
        List<Region> regions = new ArrayList<>();
        regions.add(Region.builder().name("Seoul").build());

        given(regionService.getResions()).willReturn(regions);

        mvc.perform(get("/regions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Seoul"))
        ;
    }

    /**
     * 리전 추가
     */

    @Test
    public void create() throws Exception {
        Region region = Region.builder().name("Seoul").build();
        ObjectMapper op = new ObjectMapper();

        // regionService.addRegion() 실행 시 반환할 가짜 객체
        given(regionService.addRegion(any())).will(invocation -> {
            Region region1 = invocation.getArgument(0);
            region1.setId(1234L);
            return region1;
        });

        mvc.perform(post("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(op.writeValueAsString(region))
        )
        .andExpect(status().isCreated())
        .andExpect(header().string("location","/regions/1234"))
        ;

        verify(regionService).addRegion(any()); // regionService.addRegion가 실행되었는지 확인
    }
}