package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.MenuItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private MenuItemService menuItemService;

    /**
     * 여러 메뉴 한번에 수정하기
     * @throws Exception
     */
    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(
                patch("/restaurants/12/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]")
        )
        .andExpect(status().isOk())
        .andDo(print())
        ;

        verify(menuItemService).bulkUpdate(eq(12L), any()); //여기서 eq()는 equal의 약자. 파라미터가 1L과 같다라는 뜻
    }
}