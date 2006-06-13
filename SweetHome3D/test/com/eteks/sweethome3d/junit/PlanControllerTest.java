/*
 * PlanControllerTest.java 31 mai 2006
 * 
 * Copyright (c) 2006 Emmanuel PUYBARET / eTeks <info@eteks.com>. All Rights
 * Reserved.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.eteks.sweethome3d.junit;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

import junit.framework.TestCase;

import com.eteks.sweethome3d.io.DefaultUserPreferences;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.model.Wall;
import com.eteks.sweethome3d.model.WallEvent;
import com.eteks.sweethome3d.model.WallListener;
import com.eteks.sweethome3d.swing.PlanComponent;
import com.eteks.sweethome3d.swing.PlanController;

/**
 * Tests {@link com.eteks.sweethome3d.swing.PlanController plan controller}.
 * @author Emmanuel Puybaret
 */
public class PlanControllerTest extends TestCase {
  /**
   * Performs the same tests as {@link PlanComponentTest#testPlanComponent()} 
   * but with direct calls to controller in memory.
   */
  public void testPlanContoller() {
    // 1. Create a frame that displays a PlanComponent instance of 
    // a new home at 540 pixels by 400 preferred size, and a tool bar
    // with a mode toggle button, an undo button and a redo button
    Home home = new Home();
    UserPreferences preferences = new DefaultUserPreferences();
    UndoableEditSupport undoSupport = new UndoableEditSupport();
    UndoManager undoManager = new UndoManager();
    undoSupport.addUndoableEditListener(undoManager);
    PlanController planController = new PlanController(home, preferences, undoSupport);
    // Set plan component preferred size to 540 pixels by 400
    PlanComponent plan = (PlanComponent)planController.getView();
    plan.setSize(new Dimension(540, 400));
    
    // Build an ordered list of walls added to home
    final ArrayList<Wall> orderedWalls = new ArrayList<Wall>();
    home.addWallListener(new WallListener () {
      public void wallChanged(WallEvent ev) {
        if (ev.getType() == WallEvent.Type.ADD) {
          orderedWalls.add(ev.getWall());
        }
      }
    });
    
    // 2. Use WALL_CREATION mode
    planController.setMode(PlanController.Mode.WALL_CREATION);
    // Click at (20, 20), (270, 21), (269, 170), then double click at (20, 171)
    planController.moveMouse(20, 20);
    planController.pressMouse(20, 20, 1, false);
    planController.setMagnetismDisabled(false);
    planController.releaseMouse(20, 20);
    planController.moveMouse(270, 21);
    planController.pressMouse(270, 21, 1, false);
    planController.releaseMouse(270, 21);
    planController.moveMouse(269, 170);
    planController.pressMouse(269, 170, 1, false);
    planController.releaseMouse(269, 170);
    planController.moveMouse(20, 171);
    planController.pressMouse(20, 171, 1, false);
    planController.releaseMouse(20, 171);
    planController.pressMouse(20, 171, 2, false);
    planController.releaseMouse(20, 171);
    // Check 3 walls were created at (0, 0), (500, 0), (500, 300) and (0, 300) coordinates
    Wall wall1 = orderedWalls.get(0);
    assertCoordinatesEqualWallPoints(0, 0, 500, 0, wall1);
    Wall wall2 = orderedWalls.get(1);
    assertCoordinatesEqualWallPoints(500, 0, 500, 300, wall2);
    Wall wall3 = orderedWalls.get(2);
    assertCoordinatesEqualWallPoints(500, 300, 0, 300, wall3);
    // Check they are joined to each other end point
    assertWallsAreJoined(null, wall1, wall2); 
    assertWallsAreJoined(wall1, wall2, wall3); 
    assertWallsAreJoined(wall2, wall3, null); 
    // Check they are selected
    assertSelectionContains(plan, wall3);

    // 3. Click at (20, 170), then double click at (30, 30) with Alt key depressed
    planController.moveMouse(20, 170);
    planController.pressMouse(20, 170, 1, false);
    planController.releaseMouse(20, 170);
    planController.setMagnetismDisabled(true);
    planController.moveMouse(30, 30);
    planController.pressMouse(30, 30, 1, false);
    planController.releaseMouse(30, 30);
    planController.pressMouse(30, 30, 2, false);
    planController.releaseMouse(30, 30);
    planController.setMagnetismDisabled(false);
    // Check a forth wall was created at (0, 300), (20, 20) coordinates
    Wall wall4 = orderedWalls.get(orderedWalls.size() - 1);
    assertCoordinatesEqualWallPoints(0, 300, 20, 20, wall4);
    assertSelectionContains(plan, wall4);
    assertWallsAreJoined(wall3, wall4, null);

    // 4. Use SELECTION mode
    planController.setMode(PlanController.Mode.SELECTION);
    // Check current mode is SELECTION
    assertEquals("Current mode isn't " + PlanController.Mode.SELECTION, 
        PlanController.Mode.SELECTION, planController.getMode());
    // Press the delete key
    planController.deleteSelection();
    // Check plan contains only the first three walls
    assertHomeContains(home, wall1, wall2, wall3);
    
    // 5. Use WALL_CREATION mode
    planController.setMode(PlanController.Mode.WALL_CREATION);
    //  Click at (21, 19), then double click at (20, 170)
    planController.moveMouse(21, 19);
    planController.pressMouse(21, 19, 1, false);
    planController.releaseMouse(21, 19);
    planController.moveMouse(20, 170);
    planController.pressMouse(20, 170, 1, false);
    planController.releaseMouse(20, 170);
    planController.pressMouse(20, 170, 2, false);
    planController.releaseMouse(20, 170);
    // Check a new forth wall was created at (0, 0), (0, 300) coordinates
    wall4 = orderedWalls.get(orderedWalls.size() - 1);
    assertCoordinatesEqualWallPoints(0, 0, 0, 300, wall4);
    // Check its end points are joined to the first and third wall
    assertWallsAreJoined(wall1, wall4, wall3);
    
    // 6. Use SELECTION mode
    planController.setMode(PlanController.Mode.SELECTION);
    // Drag and drop cursor from (200, 100) to (300, 180)
    planController.moveMouse(200, 100);
    planController.pressMouse(200, 100, 1, false);
    planController.moveMouse(300, 180);
    planController.releaseMouse(300, 180);
    // Check the selected walls are the second and third ones
    assertSelectionContains(plan, wall2, wall3);

    // 7. Press twice right arrow key     
    planController.moveSelection(1, 0);
    planController.moveSelection(1, 0);
    // Check the 4 walls coordinates are (0, 0), (504, 0), (504, 300), (4, 300) 
    assertCoordinatesEqualWallPoints(0, 0, 504, 0, wall1);
    assertCoordinatesEqualWallPoints(504, 0, 504, 300, wall2);
    assertCoordinatesEqualWallPoints(504, 300, 4, 300, wall3);
    assertCoordinatesEqualWallPoints(0, 0, 4, 300, wall4);

    // 8. Click at (272, 40) with Shift key depressed
    planController.moveMouse(272, 40);
    planController.pressMouse(272, 40, 1, true);
    planController.releaseMouse(272, 40);
    // Check the second wall was removed from selection
    assertSelectionContains(plan, wall3);

     // 9. Drag cursor from (50, 20) to (50, 40) 
    planController.moveMouse(50, 20);
    planController.pressMouse(50, 20, 1, false);
    planController.moveMouse(50, 40);
    // Check first wall is selected and that it moved
    assertSelectionContains(plan, wall1);
    assertCoordinatesEqualWallPoints(0, 40, 504, 40, wall1);
    // Lose focus
    planController.escape();
    // Check the wall didn't move at end
    assertCoordinatesEqualWallPoints(0, 0, 504, 0, wall1);

    // 10. Undo 8 times 
    for (int i = 0; i < 8; i++) {
      undoManager.undo();
    }
    // Check home doesn't contain any wall
    assertHomeContains(home);
    
    // 11. Redo 8 times 
    for (int i = 0; i < 8; i++) {
      undoManager.redo();
    }
    // Check plan contains the four wall
    assertHomeContains(home, wall1, wall2, wall3, wall4);
    // Check the second and the third wall are selected
    assertSelectionContains(plan, wall2, wall3);
  }

  /**
   * Asserts the start point and the end point of 
   * <code>wall</code> are at (<code>xStart</code>, <code>yStart</code>), (<code>xEnd</code>, <code>yEnd</code>). 
   */
  private void assertCoordinatesEqualWallPoints(float xStart, float yStart, float xEnd, float yEnd, Wall wall) {
    assertEquals("Incorrect X start", xStart, wall.getXStart());
    assertEquals("Incorrect Y start", yStart, wall.getYStart());
    assertEquals("Incorrect X end", xEnd, wall.getXEnd());
    assertEquals("Incorrect Y end", yEnd, wall.getYEnd());
  }

  /**
   * Asserts <code>wall</code> is joined to <code>wallAtStart</code> 
   * and <code>wallAtEnd</code>.
   */
  private void assertWallsAreJoined(Wall wallAtStart, Wall wall, Wall wallAtEnd) {
    assertSame("Incorrect wall at start", wallAtStart, wall.getWallAtStart());
    assertSame("Incorrect wall at end", wallAtEnd, wall.getWallAtEnd());
  }

  /**
   * Asserts <code>home</code> contains <code>walls</code>.
   */
  private void assertHomeContains(Home home, Wall ... walls) {
    Collection<Wall> homeWalls = home.getWalls();
    assertEquals("Home walls incorrect count", 
        walls.length, homeWalls.size());
    for (Wall wall : walls) {
      assertTrue("Wall doesn't belong to home", homeWalls.contains(wall));
    }
  }

  /**
   * Asserts <code>walls</code> are the current selected ones in <code>home</code>.
   */
  private void assertSelectionContains(PlanComponent plan, 
                                       Wall ... walls) {
    List<Wall> selectedWalls = plan.getSelectedWalls();
    assertEquals(walls.length, selectedWalls.size());
    for (Wall wall : walls) {
      assertTrue("Wall not selected", selectedWalls.contains(wall));
    }
  }
}
