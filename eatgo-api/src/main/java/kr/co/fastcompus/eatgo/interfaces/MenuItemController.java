package kr.co.fastcompus.eatgo.interfaces;


import kr.co.fastcompus.eatgo.application.MenuItemService;
import kr.co.fastcompus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public String bulkUpdate(
            @PathVariable("restaurantId") Long restaurantId
            ,@RequestBody List<MenuItem> menuItems
    ){
        menuItemService.bulkUpdate(restaurantId, menuItems);
        return "";
    }
}
