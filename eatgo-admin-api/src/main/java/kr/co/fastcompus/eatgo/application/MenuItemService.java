package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.MenuItem;
import kr.co.fastcompus.eatgo.domain.MenuItemRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Setter
@Getter
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * 메뉴 조회
     */
    public List<MenuItem> getMenuItems(Long restaurantId) {
        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }

    /**
     * 벌크 업데이트
     * 등록, 수정, 삭제를 한 번에 처리한다.
     * @param restaurantId
     * @param menuItems
     */
    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {

        menuItems.forEach(menuItem->{
            update(restaurantId, menuItem);
        });
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        menuItem.setRestaurantId(restaurantId);
        log.info("menuItem:{}", menuItem);

        // 삭제
        if(menuItem.isDestroy()) {
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }

        // id가 없으면 등록, id가 있으면 수정
        menuItemRepository.save(menuItem);
    }

}
