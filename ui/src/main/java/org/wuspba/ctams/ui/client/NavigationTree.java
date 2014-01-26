/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

/**
 *
 * @author atrimble
 */
public class NavigationTree extends TreeGrid {

    private final ExplorerTreeNode[] showcaseData = loadTree();

    public NavigationTree() {
        setWidth100();
        setHeight100();
        setSelectionType(SelectionStyle.SINGLE);
        setCustomIconProperty("icon");
        setAnimateFolderTime(100);
        setAnimateFolders(true);
        setAnimateFolderSpeed(1000);
        setNodeIcon("silk/application_view_list.png");
        setShowSortArrow(SortArrow.CORNER);
        setShowAllRecords(true);
        setLoadDataOnDemand(false);
        setCanSort(false);
        
        TreeGridField field = new TreeGridField();
        field.setCanFilter(true);
        field.setName("nodeTitle");
        field.setTitle("<b>Samples</b>");
        setFields(field);

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setNameProperty("nodeTitle");
        tree.setOpenProperty("isOpen");
        tree.setIdField("nodeID");
        tree.setParentIdField("parentNodeID");
        tree.setRootValue("root");

        tree.setData(showcaseData);

        setData(tree);
    }

    private ExplorerTreeNode[] loadTree() {
        ExplorerTreeNode[] data = new ExplorerTreeNode[] {
            new ExplorerTreeNode("Bands", 
                    null, 
                    "band-category", 
                    "root", 
                    "/images/band.png", 
                    null, 
                    true, 
                    true),
            new ExplorerTreeNode("Explore", 
                    "Band Explorer", 
                    "band-explore", 
                    "band-category", 
                    "/images/tree.png", 
                    new BandExplorerPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Registration", 
                    "Band Registration", 
                    "band-registration", 
                    "band-category", 
                    "/images/registration.png", 
                    new BandRegistrationPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Rosters", 
                    "Rosters", 
                    "band-rosters", 
                    "band-category", 
                    "/images/roster.png", 
                    new RosterPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("People", 
                    null, 
                    "people-category", 
                    "root", 
                    "/images/solo.png", 
                    null, 
                    true, 
                    true),
            new ExplorerTreeNode("Explore", 
                    "People Explorer", 
                    "people-explore", 
                    "people-category", 
                    "/images/tree.png", 
                    new PeopleExplorerPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Solo Registration", 
                    "Solo Registration", 
                    "solo-registration", 
                    "people-category", 
                    "/images/registration.png", 
                    new SoloRegistrationPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Judges", 
                    "Judge Explorer", 
                    "judge-explore", 
                    "people-category", 
                    "/images/judge.png", 
                    new JudgeExplorerPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Results", 
                    null, 
                    "result-category", 
                    "root", 
                    "/images/results.png",  
                    null, 
                    true, 
                    true),
            new ExplorerTreeNode("Bands", 
                    "Band Results", 
                    "band-results", 
                    "result-category", 
                    "/images/band.png", 
                    new BandResultsPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Soloists", 
                    "Solo Results", 
                    "solo-results", 
                    "result-category", 
                    "/images/solo.png", 
                    new SoloResultsPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Contests", 
                    "Contest Results", 
                    "contest-results", 
                    "result-category", 
                    "/images/contest.png", 
                    new ContestResultsPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Contests", 
                    null, 
                    "contest-category", 
                    "root", 
                    "/images/contest.png", 
                    null, 
                    true, 
                    true),
            new ExplorerTreeNode("Explore", 
                    "Contest Explorer", 
                    "contest-explore", 
                    "contest-category", 
                    "/images/tree.png", 
                    new ContestExplorerPanel.Factory(), 
                    true, 
                    false),
            new ExplorerTreeNode("Entries", 
                    "Contest Entries", 
                    "contest-entries", 
                    "contest-category", 
                    "/images/registration.png", 
                    new ContestEntriesPanel.Factory(), 
                    true, 
                    false)
            
        };

        return data;
    }

    public ExplorerTreeNode[] getShowcaseData() {
        return showcaseData;
    }
}