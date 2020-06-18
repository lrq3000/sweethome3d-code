/*
 * WizardControllerTest.java 7 juin 07
 *
 * Copyright (c) 2007 Emmanuel PUYBARET / eTeks <info@eteks.com>. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.eteks.sweethome3d.junit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.swing.undo.UndoableEdit;

import org.json.JSONException;

import com.eteks.sweethome3d.io.HomeEditsDeserializer;
import com.eteks.sweethome3d.io.HomeFileRecorder;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeObject;
import com.eteks.sweethome3d.model.HomeRecorder;
import com.eteks.sweethome3d.model.Polyline;
import com.eteks.sweethome3d.model.RecorderException;

import junit.framework.TestCase;

/**
 * Tests {@link com.eteks.sweethome3d.io.HomeEditsDeserializer edits deserializer}.
 * @author Renaud Pawlak
 * @author Emmanuel Puybaret
 */
public class DeserializationTest extends TestCase {
  public void testEditsStreamDeserialization () throws RecorderException, JSONException, IOException, ReflectiveOperationException {
    HomeRecorder recorder = new HomeFileRecorder(0, false, null, false, true);
    File file = new File("test/resources/HomeTest.sh3d");
    Home home = recorder.readHome(file.getPath());

    File tempFile = File.createTempFile("open-", ".sh3d");
    Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    List<UndoableEdit> edits = new HomeEditsDeserializer(home, tempFile, new File(".").toURI().toURL().toString()).deserializeEdits(
          "["
        + "{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.WallsCreationUndoableEdit\",\"hasBeenDone\":true,\"alive\":true,\"presentationNameKey\":\"undoCreateWallsName\",\"oldSelection\":[],\"oldBasePlanLocked\":false,\"oldAllLevelsSelection\":false,\"joinedNewWalls\":[{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.JoinedWall\",\"wall\":\"wall-d518deed-bdea-45a2-8905-cfe89868c27e\",\"level\":null,\"xStart\":525.8488106708302,\"yStart\":100.07811,\"xEnd\":749.8488106708302,\"yEnd\":100.07811,\"wallAtStart\":null,\"wallAtEnd\":\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\",\"joinedAtEndOfWallAtStart\":false,\"joinedAtStartOfWallAtEnd\":true},{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.JoinedWall\",\"wall\":\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\",\"level\":null,\"xStart\":749.8488106708302,\"yStart\":100.07811,\"xEnd\":749.8488106708302,\"yEnd\":337.07811,\"wallAtStart\":\"wall-d518deed-bdea-45a2-8905-cfe89868c27e\",\"wallAtEnd\":\"wall-410f945f-5490-4866-9d23-6be9dab5df2d\",\"joinedAtEndOfWallAtStart\":true,\"joinedAtStartOfWallAtEnd\":true},{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.JoinedWall\",\"wall\":\"wall-410f945f-5490-4866-9d23-6be9dab5df2d\",\"level\":null,\"xStart\":749.8488106708302,\"yStart\":337.07811,\"xEnd\":749.8488106708302,\"yEnd\":337.07811000000004,\"wallAtStart\":\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\",\"wallAtEnd\":null,\"joinedAtEndOfWallAtStart\":true,\"joinedAtStartOfWallAtEnd\":false}],\"newBasePlanLocked\":false,\"_newObjects\":{\"wall-d518deed-bdea-45a2-8905-cfe89868c27e\":{\"_type\":\"com.eteks.sweethome3d.model.Wall\",\"id\":\"wall-d518deed-bdea-45a2-8905-cfe89868c27e\",\"properties\":null,\"xStart\":525.8488106708302,\"yStart\":100.07811,\"xEnd\":749.8488106708302,\"yEnd\":100.07811,\"arcExtent\":null,\"wallAtStart\":null,\"wallAtEnd\":\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\",\"thickness\":7.5,\"height\":250,\"heightAtEnd\":null,\"leftSideColor\":null,\"leftSideTexture\":null,\"leftSideShininess\":0,\"leftSideBaseboard\":null,\"rightSideColor\":null,\"rightSideTexture\":null,\"rightSideShininess\":0,\"rightSideBaseboard\":null,\"pattern\":{\"_type\":\"com.eteks.sweethome3d.io.DefaultPatternTexture\",\"name\":\"hatchUp\",\"image\":{\"_type\":\"com.eteks.sweethome3d.tools.URLContent\",\"url\":\"http://localhost:8080/lib//resources/patterns/hatchUp.png\"}},\"topColor\":null,\"level\":null,\"shapeCache\":{\"_type\":\"java.awt.geom.GeneralPath\",\"pointTypes\":[0,1,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\"numTypes\":5,\"numCoords\":8,\"windingRule\":1,\"floatCoords\":[525.8488106708302,96.32811,753.5988106708302,96.32811,746.0988106708302,103.82811,525.8488106708302,103.82811,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]},\"arcCircleCenterCache\":null,\"pointsCache\":[[525.8488106708302,96.32811],[753.5988106708302,96.32811],[746.0988106708302,103.82811],[525.8488106708302,103.82811]],\"pointsIncludingBaseboardsCache\":null,\"symmetric\":true},\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\":{\"_type\":\"com.eteks.sweethome3d.model.Wall\",\"id\":\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\",\"properties\":null,\"xStart\":749.8488106708302,\"yStart\":100.07811,\"xEnd\":749.8488106708302,\"yEnd\":337.07811,\"arcExtent\":null,\"wallAtStart\":\"wall-d518deed-bdea-45a2-8905-cfe89868c27e\",\"wallAtEnd\":\"wall-410f945f-5490-4866-9d23-6be9dab5df2d\",\"thickness\":7.5,\"height\":250,\"heightAtEnd\":null,\"leftSideColor\":null,\"leftSideTexture\":null,\"leftSideShininess\":0,\"leftSideBaseboard\":null,\"rightSideColor\":null,\"rightSideTexture\":null,\"rightSideShininess\":0,\"rightSideBaseboard\":null,\"pattern\":{\"_type\":\"com.eteks.sweethome3d.io.DefaultPatternTexture\",\"name\":\"hatchUp\",\"image\":{\"_type\":\"com.eteks.sweethome3d.tools.URLContent\",\"url\":\"http://localhost:8080/lib//resources/patterns/hatchUp.png\"}},\"topColor\":null,\"level\":null,\"shapeCache\":{\"_type\":\"java.awt.geom.GeneralPath\",\"pointTypes\":[0,1,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\"numTypes\":5,\"numCoords\":8,\"windingRule\":1,\"floatCoords\":[753.5988106708302,96.32811,753.5988106708302,337.07811,746.0988106708302,337.07811,746.0988106708302,103.82811,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]},\"arcCircleCenterCache\":null,\"pointsCache\":[[753.5988106708302,96.32811],[753.5988106708302,337.07811],[746.0988106708302,337.07811],[746.0988106708302,103.82811]],\"pointsIncludingBaseboardsCache\":null,\"symmetric\":true},\"wall-410f945f-5490-4866-9d23-6be9dab5df2d\":{\"_type\":\"com.eteks.sweethome3d.model.Wall\",\"id\":\"wall-410f945f-5490-4866-9d23-6be9dab5df2d\",\"properties\":null,\"xStart\":749.8488106708302,\"yStart\":337.07811,\"xEnd\":749.8488106708302,\"yEnd\":337.07811000000004,\"arcExtent\":null,\"wallAtStart\":\"wall-d5042d56-fbfc-4257-bd22-ce7dad2a4170\",\"wallAtEnd\":null,\"thickness\":7.5,\"height\":250,\"heightAtEnd\":null,\"leftSideColor\":null,\"leftSideTexture\":null,\"leftSideShininess\":0,\"leftSideBaseboard\":null,\"rightSideColor\":null,\"rightSideTexture\":null,\"rightSideShininess\":0,\"rightSideBaseboard\":null,\"pattern\":{\"_type\":\"com.eteks.sweethome3d.io.DefaultPatternTexture\",\"name\":\"hatchUp\",\"image\":{\"_type\":\"com.eteks.sweethome3d.tools.URLContent\",\"url\":\"http://localhost:8080/lib//resources/patterns/hatchUp.png\"}},\"topColor\":null,\"level\":null,\"shapeCache\":null,\"arcCircleCenterCache\":null,\"pointsCache\":[[753.5988106708302,337.07811],[753.5988106708302,337.07811000000004],[746.0988106708302,337.07811000000004],[746.0988106708302,337.07811]],\"pointsIncludingBaseboardsCache\":null,\"symmetric\":true}}}"
        + ","
        + "{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.RoomsCreationUndoableEdit\",\"hasBeenDone\":true,\"alive\":true,\"presentationNameKey\":\"undoCreateRoomsName\",\"oldSelection\":[],\"oldBasePlanLocked\":false,\"oldAllLevelsSelection\":false,\"newRooms\":[\"room-4aab99b2-1ef2-4d8b-a978-f8cd08d55f18\"],\"roomsIndex\":[2],\"roomsLevel\":null,\"newBasePlanLocked\":false,\"_newObjects\":{\"room-4aab99b2-1ef2-4d8b-a978-f8cd08d55f18\":{\"_type\":\"com.eteks.sweethome3d.model.Room\",\"id\":\"room-4aab99b2-1ef2-4d8b-a978-f8cd08d55f18\",\"properties\":null,\"name\":null,\"nameXOffset\":0,\"nameYOffset\":-40,\"nameStyle\":null,\"nameAngle\":0,\"points\":[[525.8488,103.82811],[746.0988,103.82811],[746.0988,337.07812],[525.8488,337.07811999999996]],\"areaVisible\":true,\"areaXOffset\":0,\"areaYOffset\":0,\"areaStyle\":null,\"areaAngle\":0,\"floorVisible\":true,\"floorColor\":null,\"floorTexture\":null,\"floorShininess\":0,\"ceilingVisible\":false,\"ceilingColor\":null,\"ceilingTexture\":null,\"ceilingShininess\":0,\"level\":null,\"shapeCache\":{\"_type\":\"java.awt.geom.GeneralPath\",\"pointTypes\":[0,1,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\"numTypes\":5,\"numCoords\":8,\"windingRule\":1,\"floatCoords\":[525.8488,103.82811,746.0988,103.82811,746.0988,337.07812,525.8488,337.07811999999996,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]},\"areaCache\":51373.31470250001}}}"
        + "]"
    );
    for (UndoableEdit edit : edits) {
      edit.redo();
    }
    assertEquals("Wrong undo count", 2, edits.size());

    File savedFile = File.createTempFile("save-", ".sh3d");
    recorder.writeHome(home, savedFile.getPath());
    assertTrue("Empty file", savedFile.length() > 0);

    tempFile.delete();
    savedFile.delete();
  }
  
  public void testResizingEdit () throws RecorderException, JSONException, IOException, ReflectiveOperationException {
    HomeRecorder recorder = new HomeFileRecorder(0, false, null, false, true);
    File file = new File("test/resources/HomeTest.sh3d");
    Home home = recorder.readHome(file.getPath());

    File tempFile = File.createTempFile("open-", ".sh3d");
    Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    Optional<HomeObject> homeObject = (Optional<HomeObject>)home.getHomeObjects().stream().filter(o -> "polyline-778b729f-47a8-4c70-a086-be423eceba59".equals(o.getId())).findFirst();
    assertTrue(homeObject.isPresent());
    assertTrue(homeObject.get() instanceof Polyline);
    Polyline polyline = (Polyline)homeObject.get();
    assertEquals(68.08908081, polyline.getPoints()[1][0], 0.0001);
    assertEquals(182.87162780, polyline.getPoints()[1][1], 0.0001);
    
    List<UndoableEdit> edits = new HomeEditsDeserializer(home, tempFile, new File(".").toURI().toURL().toString()).deserializeEdits(
          "["
        + "{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.PolylineResizingUndoableEdit\",\"oldX\":-55.91092,\"oldY\":177.5383,\"polyline\":\"polyline-778b729f-47a8-4c70-a086-be423eceba59\",\"pointIndex\":1,\"newX\":-80.99411,\"newY\":165.1319}"
        + "]"
    );
    assertEquals(1, edits.size());
    HomeEditsDeserializer.applyEdits(edits);

    assertEquals(-80.99411, polyline.getPoints()[1][0], 0.0001);
    assertEquals(165.1319, polyline.getPoints()[1][1], 0.0001);

    File savedFile = File.createTempFile("save-", ".sh3d");
    recorder.writeHome(home, savedFile.getPath());
    assertTrue("Empty file", savedFile.length() > 0);

    tempFile.delete();
    savedFile.delete();
  }

  public void testMovingEdit () throws RecorderException, JSONException, IOException, ReflectiveOperationException {
    HomeRecorder recorder = new HomeFileRecorder(0, false, null, false, true);
    File file = new File("test/resources/HomeTest.sh3d");
    Home home = recorder.readHome(file.getPath());

    File tempFile = File.createTempFile("open-", ".sh3d");
    Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    Optional<HomeObject> homeObject = (Optional<HomeObject>)home.getHomeObjects().stream().filter(o -> "polyline-778b729f-47a8-4c70-a086-be423eceba59".equals(o.getId())).findFirst();
    assertTrue(homeObject.isPresent());
    assertTrue(homeObject.get() instanceof Polyline);
    Polyline polyline = (Polyline)homeObject.get();
    assertEquals(68.08908081, polyline.getPoints()[1][0], 0.0001);
    assertEquals(182.87162780, polyline.getPoints()[1][1], 0.0001);
    
    List<UndoableEdit> edits = new HomeEditsDeserializer(home, tempFile, new File(".").toURI().toURL().toString()).deserializeEdits(
          "["
        + "{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.PieceOfFurnitureMovingUndoableEdit\",\"oldAngle\":4.3597717,\"oldDepth\":49.899998,\"oldElevation\":0,\"oldDoorOrWindowBoundToWall\":false,\"piece\":\"pieceOfFurniture-6b4c53e1-745e-4581-930b-c8e3a07a0d6b\",\"dx\":429.33333333333337,\"dy\":20,\"newAngle\":4.3597717,\"newDepth\":49.899998,\"newElevation\":0}"
        + ","      
        + "{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.ItemsMovingUndoableEdit\",\"oldSelection\":[\"polyline-778b729f-47a8-4c70-a086-be423eceba59\"],\"allLevelsSelection\":false,\"itemsArray\":[\"polyline-778b729f-47a8-4c70-a086-be423eceba59\"],\"dx\":-18.666666666666657,\"dy\":-6.666666666666629}"
        + "]"
    );
    assertEquals(2, edits.size());
    HomeEditsDeserializer.applyEdits(edits);

    assertEquals(68.08908081 - 18.666666666666657, polyline.getPoints()[1][0], 0.0001);
    assertEquals(182.87162780 - 6.666666666666629, polyline.getPoints()[1][1], 0.0001);

    File savedFile = File.createTempFile("save-", ".sh3d");
    recorder.writeHome(home, savedFile.getPath());
    assertTrue("Empty file", savedFile.length() > 0);

    tempFile.delete();
    savedFile.delete();
  }
  
  public void testCopyPasteEdit () throws RecorderException, JSONException, IOException, ReflectiveOperationException {
    HomeRecorder recorder = new HomeFileRecorder(0, false, null, false, true);
    File file = new File("test/resources/HomeTest.sh3d");
    Home home = recorder.readHome(file.getPath());

    File tempFile = File.createTempFile("open-", ".sh3d");
    Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    Optional<HomeObject> homeObject = (Optional<HomeObject>)home.getHomeObjects().stream().filter(o -> "polyline-778b729f-47a8-4c70-a086-be423eceba59".equals(o.getId())).findFirst();
    assertTrue(homeObject.isPresent());
    assertTrue(homeObject.get() instanceof Polyline);
    Polyline polyline = (Polyline)homeObject.get();
    assertEquals(68.08908081, polyline.getPoints()[1][0], 0.0001);
    assertEquals(182.87162780, polyline.getPoints()[1][1], 0.0001);
    
    List<UndoableEdit> edits = new HomeEditsDeserializer(home, tempFile, new File(".").toURI().toURL().toString()).deserializeEdits(
          "["
        + "{\"_type\":\"javax.swing.undo.CompoundEdit\",\"edits\":[{\"_type\":\"com.eteks.sweethome3d.viewcontroller.FurnitureController.FurnitureAdditionUndoableEdit\",\"allLevelsSelection\":false,\"oldSelection\":[\"polyline-778b729f-47a8-4c70-a086-be423eceba59\"],\"oldBasePlanLocked\":false,\"newFurniture\":[],\"newFurnitureIndex\":[],\"newFurnitureGroups\":null,\"newFurnitureLevels\":null,\"furnitureLevel\":null,\"newBasePlanLocked\":false},{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.PolylinesCreationUndoableEdit\",\"oldSelection\":[],\"oldBasePlanLocked\":false,\"oldAllLevelsSelection\":false,\"newPolylines\":[\"polyline-78fb01da-921d-4246-a38b-1e83ff09f404\"],\"polylinesIndex\":[3],\"polylinesLevel\":null,\"newBasePlanLocked\":false},{\"_type\":\"com.eteks.sweethome3d.viewcontroller.PlanController.ItemsAdditionEndUndoableEdit\",\"items\":[\"polyline-78fb01da-921d-4246-a38b-1e83ff09f404\"]},{\"_type\":\"com.eteks.sweethome3d.viewcontroller.LocalizedUndoableEdit\"}],\"_newObjects\":{\"polyline-78fb01da-921d-4246-a38b-1e83ff09f404\":{\"_type\":\"com.eteks.sweethome3d.model.Polyline\",\"id\":\"polyline-78fb01da-921d-4246-a38b-1e83ff09f404\",\"properties\":null,\"points\":[[653.3392233333334,193.13189666666662],[605.4224133333333,205.53829666666664],[618.4499133333334,246.36086666666662],[583.4999133333333,257.85329666666667]],\"thickness\":3,\"capStyleName\":null,\"joinStyleName\":null,\"dashStyleName\":null,\"dashPattern\":null,\"dashOffset\":0.4,\"startArrowStyleName\":null,\"endArrowStyleName\":null,\"closedPath\":false,\"color\":-1638351,\"elevation\":0,\"level\":null}}}"
        + "]"
    );
    assertEquals(1, edits.size());
    HomeEditsDeserializer.applyEdits(edits);

    File savedFile = File.createTempFile("save-", ".sh3d");
    recorder.writeHome(home, savedFile.getPath());
    assertTrue("Empty file", savedFile.length() > 0);

    tempFile.delete();
    savedFile.delete();
  }

  
}