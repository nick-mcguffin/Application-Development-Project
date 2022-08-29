package com.wilma.entity.navigation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SidebarMenuElement {
    private String page;
    private String path;
    private String icon;
    private String displayName;
}