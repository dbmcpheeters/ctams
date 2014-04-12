package org.wuspba.ctams.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CTAMS implements EntryPoint {

    private TabSet mainTabSet;
    private Menu contextMenu;
    private NavigationTree sideNav;

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {

        mainTabSet = new TabSet();
        Layout paneContainerProperties = new Layout();
        paneContainerProperties.setLayoutMargin(0);
        paneContainerProperties.setLayoutTopMargin(1);
        mainTabSet.setPaneContainerProperties(paneContainerProperties);

        mainTabSet.setWidth100();
        mainTabSet.setHeight100();
        mainTabSet.addTabSelectedHandler(new TabSelectedHandler() {
            @Override
            public void onTabSelected(TabSelectedEvent event) {
                Tab selectedTab = event.getTab();
                String historyToken = selectedTab.getAttribute("historyToken");
                if (historyToken != null) {
                    History.newItem(historyToken, false);
                } else {
                    History.newItem("main", false);
                }
            }
        });
        mainTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER);

        contextMenu = createContextMenu();

        Tab tab = new Tab();
        tab.setTitle("Home&nbsp;&nbsp;");
        tab.setIcon("/ctams-ui/images/house.png", 16);
        tab.setWidth(80);
        tab.setCanClose(false);
        tab.setContextMenu(contextMenu);

        HLayout mainPanel = new HLayout();
        mainPanel.setHeight100();
        mainPanel.setWidth100();

        HomePanel home = new HomePanel();
        mainPanel.addMember(home);

        tab.setPane(mainPanel);
        mainTabSet.addTab(tab);

        VLayout sideNavLayout = new VLayout();
        sideNavLayout.setHeight100();
        sideNavLayout.setWidth(215);
        sideNavLayout.setShowResizeBar(true);

        sideNav = new NavigationTree();
        sideNav.setID("isc_SideNavTree_0");
        sideNav.addLeafClickHandler(new LeafClickHandler() {
            @Override
            public void onLeafClick(LeafClickEvent event) {
                TreeNode node = event.getLeaf();
                showSample(node);
            }
        });
        sideNavLayout.addMember(sideNav);

        HLayout contentLayout = new HLayout();
        contentLayout.setMembersMargin(20);
        contentLayout.setHeight100();
        contentLayout.setWidth100();

        VLayout mainLayout = new VLayout();
        mainLayout.setMembersMargin(0);
        mainLayout.setHeight100();
        mainLayout.setWidth100();

        VLayout forms = new VLayout();
        forms.setMembersMargin(10);

        forms.addMember(mainTabSet);

        ToolStrip topBar = new ToolStrip();
        topBar.setHeight(33);
        topBar.setWidth100();

        topBar.addSpacer(6);
        ImgButton sgwtHomeButton = new ImgButton();
        sgwtHomeButton.setSrc("/ctams-ui/images/logo.jpg");
        sgwtHomeButton.setWidth(73);
        sgwtHomeButton.setHeight(33);
        sgwtHomeButton.setPrompt("WUSPBA");
        sgwtHomeButton.setHoverStyle("interactImageHover");
        sgwtHomeButton.setShowRollOver(false);
        sgwtHomeButton.setShowDownIcon(false);
        sgwtHomeButton.setShowDown(false);
        sgwtHomeButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                com.google.gwt.user.client.Window.open("http://www.wuspba.org/",
                        "sgwt", null);
            }
        });
        topBar.addMember(sgwtHomeButton);
        topBar.addSpacer(6);

        Label title = new Label("CTAMS - Competition Tracking and Management System");
        title.setStyleName("sgwtTitle");
        title.setWidth(500);
        topBar.addMember(title);

        topBar.addFill();
        
        IButton devConsole = new IButton("Developer's Console");
        devConsole.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SC.showConsole();
            }
        });
        topBar.addMember(devConsole);

        contentLayout.addMember(sideNavLayout);
        contentLayout.addMember(forms);

        mainLayout.addMember(topBar);
        mainLayout.addMember(contentLayout);
        mainLayout.draw();

    }

    protected void showSample(TreeNode node) {
        ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode) node;
        PanelFactory factory = explorerTreeNode.getFactory();

        if (factory != null) {
            String panelID = factory.getID();
            Tab tab = null;
            if (panelID != null) {
                String tabID = panelID + "_tab";
                tab = mainTabSet.getTab(tabID);
            }
            if (tab == null) {
                Canvas panel = factory.create();
                tab = new Tab();
                tab.setID(factory.getID() + "_tab");
                    //store history token on tab so that when an already open is selected, one can retrieve the
                //history token and update the URL
                tab.setAttribute("historyToken", explorerTreeNode.getNodeID());
                tab.setContextMenu(contextMenu);

                String sampleName = explorerTreeNode.getTabName();

                String icon = explorerTreeNode.getIcon();
                if (icon == null) {
                    icon = "silk/plugin.png";
                }
                String imgHTML = Canvas.imgHTML(icon, 16, 16);
                tab.setTitle("<span>" + imgHTML + "&nbsp;" + sampleName + "</span>");
                tab.setPane(panel);
                tab.setCanClose(true);
                mainTabSet.addTab(tab);
                mainTabSet.selectTab(tab);
            } else {
                mainTabSet.selectTab(tab);
            }
        }
    }

    public void onHistoryChanged(String historyToken) {
        if (historyToken == null || historyToken.equals("")) {
            mainTabSet.selectTab(0);
        } else {
            ExplorerTreeNode[] showcaseData = sideNav.getShowcaseData();
            for (ExplorerTreeNode explorerTreeNode : showcaseData) {
                if (explorerTreeNode.getNodeID().equals(historyToken)) {
                    showSample(explorerTreeNode);
                    ListGridRecord selectedRecord = sideNav.getSelectedRecord();
                    if (selectedRecord != null) {
                        sideNav.deselectRecord(selectedRecord);
                    }
                    sideNav.selectRecord(explorerTreeNode);
                    Tree tree = sideNav.getData();
                    TreeNode categoryNode = tree.getParent(explorerTreeNode);
                    while (categoryNode != null && !"/".equals(tree.getName(categoryNode))) {
                        tree.openFolder(categoryNode);
                        categoryNode = tree.getParent(categoryNode);
                    }
                }
            }
        }
    }

    private Menu createContextMenu() {
        Menu menu = new Menu();
        menu.setWidth(140);

        MenuItemIfFunction enableCondition = new MenuItemIfFunction() {
            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                int selectedTab = mainTabSet.getSelectedTabNumber();
                return selectedTab != 0;
            }
        };

        MenuItem closeItem = new MenuItem("<u>C</u>lose");
        closeItem.setEnableIfCondition(enableCondition);
        closeItem.setKeyTitle("Alt+C");
        KeyIdentifier closeKey = new KeyIdentifier();
        closeKey.setAltKey(true);
        closeKey.setKeyName("C");
        closeItem.setKeys(closeKey);
        closeItem.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                int selectedTab = mainTabSet.getSelectedTabNumber();
                mainTabSet.removeTab(selectedTab);
                mainTabSet.selectTab(selectedTab - 1);
            }
        });

        MenuItem closeAllButCurrent = new MenuItem("Close Others");
        closeAllButCurrent.setEnableIfCondition(enableCondition);
        closeAllButCurrent.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                int selected = mainTabSet.getSelectedTabNumber();
                Tab[] tabs = mainTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 2];
                int cnt = 0;
                for (int i = 1; i < tabs.length; i++) {
                    if (i != selected) {
                        tabsToRemove[cnt] = i;
                        cnt++;
                    }
                }
                mainTabSet.removeTabs(tabsToRemove);
            }
        });

        MenuItem closeAll = new MenuItem("Close All");
        closeAll.setEnableIfCondition(enableCondition);
        closeAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                Tab[] tabs = mainTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 1];

                for (int i = 1; i < tabs.length; i++) {
                    tabsToRemove[i - 1] = i;
                }
                mainTabSet.removeTabs(tabsToRemove);
                mainTabSet.selectTab(0);
            }
        });

        menu.setItems(closeItem, closeAllButCurrent, closeAll);
        return menu;
    }
}
