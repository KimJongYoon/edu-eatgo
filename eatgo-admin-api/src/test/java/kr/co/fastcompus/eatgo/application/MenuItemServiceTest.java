package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.MenuItem;
import kr.co.fastcompus.eatgo.domain.MenuItemRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuItemServiceTest {

    @InjectMocks
    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    /**
     * 목록 갖고 오기
     */
    @Test
    public void getMenuItems(){
        MenuItem mockMenuItem = MenuItem.builder().menu("Kimchi").build();
        given(menuItemRepository.findAllByRestaurantId(eq(1004L))).willReturn(Arrays.asList(mockMenuItem));

        List<MenuItem> menuItems = menuItemService.getMenuItems(1004L);
        MenuItem menuItem = menuItems.get(0);

        assertThat(menuItem.getMenu(), is("Kimchi"));

    }

    /**
     * 벌크 업데이트 테스트
     * 추가, 수정, 삭제를 한 번에 할 수 있다.
     */
    @Test
    public void bulkUpdate(){
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(MenuItem.builder().menu("Kimchi").build()); // 새로 추가(save() 메서드에서 처리 됨)
        menuItems.add(MenuItem.builder().id(12L).menu("Gukbob").build()); // 수정(save() 메서드에서 처리 됨)
        menuItems.add(MenuItem.builder().id(1004L).destroy(true).build()); // 삭제(deleteById() 메서드에서 처리 됨)

        menuItemService.bulkUpdate(1L, menuItems);

        verify(menuItemRepository, times(2)).save(any()); // times는 menuItemRepository.save()가 2번 실행된다는 뜻
        verify(menuItemRepository, times(1)).deleteById(eq(1004L)); // 삭제
    }

}