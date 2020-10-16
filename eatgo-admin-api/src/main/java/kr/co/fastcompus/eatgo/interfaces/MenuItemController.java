package kr.co.fastcompus.eatgo.interfaces;


import kr.co.fastcompus.eatgo.application.MenuItemService;
import kr.co.fastcompus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;

    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> list(
            @PathVariable("restaurantId") Long restaurantId
    ){
        return menuItemService.getMenuItems(restaurantId);
    }

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public String bulkUpdate(
            @PathVariable("restaurantId") Long restaurantId
            ,@RequestBody List<MenuItem> menuItems
    ){
        menuItemService.bulkUpdate(restaurantId, menuItems);
        return "";
    }
}
