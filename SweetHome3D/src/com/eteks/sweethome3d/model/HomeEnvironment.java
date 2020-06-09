/*
 * HomeEnvironment.java 6 nov. 2008
 *
 * Sweet Home 3D, Copyright (c) 2008 Emmanuel PUYBARET / eTeks <info@eteks.com>
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
package com.eteks.sweethome3d.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The environment attributes of a home.
 * @author Emmanuel Puybaret
 */
public class HomeEnvironment extends HomeObject implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /**
   * The environment properties that may change.
   */
  public enum Property {OBSERVER_CAMERA_ELEVATION_ADJUSTED, GROUND_COLOR, GROUND_TEXTURE, BACKGROUND_IMAGE_VISIBLE_ON_GROUND_3D,
                        SKY_COLOR, SKY_TEXTURE, LIGHT_COLOR, CEILING_LIGHT_COLOR,
                        WALLS_ALPHA, DRAWING_MODE, SUBPART_SIZE_UNDER_LIGHT, ALL_LEVELS_VISIBLE,
                        PHOTO_WIDTH, PHOTO_HEIGHT, PHOTO_ASPECT_RATIO, PHOTO_QUALITY,
                        VIDEO_WIDTH, VIDEO_ASPECT_RATIO, VIDEO_QUALITY, VIDEO_SPEED, VIDEO_FRAME_RATE, VIDEO_CAMERA_PATH};
  /**
   * The various modes used to draw home in 3D.
   */
  public enum DrawingMode {
    FILL, OUTLINE, FILL_AND_OUTLINE
  }

  private boolean                         observerCameraElevationAdjusted;
  private int                             groundColor;
  private HomeTexture                     groundTexture;
  private boolean                         backgroundImageVisibleOnGround3D;
  private int                             skyColor;
  private HomeTexture                     skyTexture;
  private int                             lightColor;
  private int                             ceilingLightColor;
  private float                           wallsAlpha;
  private DrawingMode                     drawingMode;
  private float                           subpartSizeUnderLight;
  private boolean                         allLevelsVisible;
  private int                             photoWidth;
  private int                             photoHeight;
  private transient AspectRatio           photoAspectRatio;
  // Aspect ratios are saved as a string to be able to keep backward compatibility
  // if new constants are added to AspectRatio enum in future versions
  private String                          photoAspectRatioName;
  private int                             photoQuality;
  private int                             videoWidth;
  private transient AspectRatio           videoAspectRatio;
  // Aspect ratios are saved as a string to be able to keep backward compatibility
  // if new constants are added to AspectRatio enum in future versions
  private String                          videoAspectRatioName;
  private int                             videoQuality;
  private float                           videoSpeed;
  private int                             videoFrameRate;
  private List<Camera>                    cameraPath;
  private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

  /**
   * Creates default environment.
   */
  public HomeEnvironment() {
    this(HomeObject.createId("environment"));
  }

  /**
   * Creates default environment.
   * @since 6.4
   */
  public HomeEnvironment(String id) {
    this(id,
         0xA8A8A8, // Ground color
         null,     // Ground texture
         0xCCE4FC, // Sky color
         null,     // Sky texture
         0xD0D0D0, // Light color
         0);       // Walls alpha
  }

  /**
   * Creates home environment from parameters.
   */
  public HomeEnvironment(int groundColor,
                         HomeTexture groundTexture, int skyColor,
                         int lightColor, float wallsAlpha) {
    this(groundColor, groundTexture, skyColor, null,
        lightColor, wallsAlpha);
  }

  /**
   * Creates home environment from parameters.
   * @since 2.2
   */
  public HomeEnvironment(int groundColor, HomeTexture groundTexture,
                         int skyColor, HomeTexture skyTexture,
                         int lightColor, float wallsAlpha) {
    this(HomeObject.createId("environment"), groundColor, groundTexture,
        skyColor, skyTexture, lightColor, wallsAlpha);
  }

  /**
   * Creates home environment from parameters.
   * @since 6.4
   */
  public HomeEnvironment(String id,
                         int groundColor, HomeTexture groundTexture,
                         int skyColor, HomeTexture skyTexture,
                         int lightColor, float wallsAlpha) {
    super(id);
    this.observerCameraElevationAdjusted = true;
    this.groundColor = groundColor;
    this.groundTexture = groundTexture;
    this.skyColor = skyColor;
    this.skyTexture = skyTexture;
    this.lightColor = lightColor;
    this.ceilingLightColor = 0xD0D0D0;
    this.wallsAlpha = wallsAlpha;
    this.drawingMode = DrawingMode.FILL;
    this.photoWidth = 400;
    this.photoHeight = 300;
    this.photoAspectRatio = AspectRatio.VIEW_3D_RATIO;
    this.videoWidth = 320;
    this.videoAspectRatio = AspectRatio.RATIO_4_3;
    this.videoSpeed = 2400f / 3600; // 2.4 km/h
    this.videoFrameRate = 25;
    this.cameraPath = Collections.emptyList();
  }

  /**
   * Initializes environment transient fields
   * and reads attributes from <code>in</code> stream with default reading method.
   */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    this.propertyChangeSupport = new PropertyChangeSupport(this);
    this.ceilingLightColor = 0xD0D0D0;
    this.photoWidth = 400;
    this.photoHeight = 300;
    this.photoAspectRatio = AspectRatio.VIEW_3D_RATIO;
    this.videoWidth = 320;
    this.videoAspectRatio = AspectRatio.RATIO_4_3;
    this.videoSpeed = 2400f / 3600;
    this.videoFrameRate = 25;
    this.cameraPath = Collections.emptyList();
    in.defaultReadObject();
    try {
      // Read aspect from a string
      if (this.photoAspectRatioName != null) {
        this.photoAspectRatio = AspectRatio.valueOf(this.photoAspectRatioName);
      }
    } catch (IllegalArgumentException ex) {
      // Ignore malformed enum constant
    }
    try {
      // Read aspect from a string
      if (this.videoAspectRatioName != null) {
        this.videoAspectRatio = AspectRatio.valueOf(this.videoAspectRatioName);
      }
    } catch (IllegalArgumentException ex) {
      // Ignore malformed enum constant
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws IOException {
    // Write aspect ratios as strings to be able to read aspect ratio later
    // even if enum changed in later versions
    this.photoAspectRatioName = this.photoAspectRatio.name();
    this.videoAspectRatioName = this.videoAspectRatio.name();
    out.defaultWriteObject();
  }

  /**
   * Adds the property change <code>listener</code> in parameter to this environment.
   */
  public void addPropertyChangeListener(Property property, PropertyChangeListener listener) {
    this.propertyChangeSupport.addPropertyChangeListener(property.name(), listener);
  }

  /**
   * Removes the property change <code>listener</code> in parameter from this environment.
   */
  public void removePropertyChangeListener(Property property, PropertyChangeListener listener) {
    this.propertyChangeSupport.removePropertyChangeListener(property.name(), listener);
  }

  /**
   * Returns <code>true</code> if the observer elevation should be adjusted according
   * to the elevation of the selected level.
   * @since 3.5
   */
  public boolean isObserverCameraElevationAdjusted() {
    return this.observerCameraElevationAdjusted;
  }

  /**
   * Sets whether the observer elevation should be adjusted according
   * to the elevation of the selected level and fires a <code>PropertyChangeEvent</code>.
   * @since 3.5
   */
  public void setObserverCameraElevationAdjusted(boolean observerCameraElevationAdjusted) {
    if (this.observerCameraElevationAdjusted != observerCameraElevationAdjusted) {
      this.observerCameraElevationAdjusted = observerCameraElevationAdjusted;
      this.propertyChangeSupport.firePropertyChange(Property.OBSERVER_CAMERA_ELEVATION_ADJUSTED.name(),
          !observerCameraElevationAdjusted, observerCameraElevationAdjusted);
    }
  }

  /**
   * Returns the ground color of this environment.
   */
  public int getGroundColor() {
    return this.groundColor;
  }

  /**
   * Sets the ground color of this environment and fires a <code>PropertyChangeEvent</code>.
   */
  public void setGroundColor(int groundColor) {
    if (groundColor != this.groundColor) {
      int oldGroundColor = this.groundColor;
      this.groundColor = groundColor;
      this.propertyChangeSupport.firePropertyChange(
          Property.GROUND_COLOR.name(), oldGroundColor, groundColor);
    }
  }

  /**
   * Returns the ground texture of this environment.
   */
  public HomeTexture getGroundTexture() {
    return this.groundTexture;
  }

  /**
   * Sets the ground texture of this environment and fires a <code>PropertyChangeEvent</code>.
   */
  public void setGroundTexture(HomeTexture groundTexture) {
    if (groundTexture != this.groundTexture) {
      HomeTexture oldGroundTexture = this.groundTexture;
      this.groundTexture = groundTexture;
      this.propertyChangeSupport.firePropertyChange(
          Property.GROUND_TEXTURE.name(), oldGroundTexture, groundTexture);
    }
  }

  /**
   * Returns <code>true</code> if the background image should be displayed on the ground in 3D.
   * @since 6.0
   */
  public boolean isBackgroundImageVisibleOnGround3D() {
    return this.backgroundImageVisibleOnGround3D;
  }

  /**
   * Sets whether the background image should be displayed on the ground in 3D and
   * fires a <code>PropertyChangeEvent</code>.
   * @since 6.0
   */
  public void setBackgroundImageVisibleOnGround3D(boolean backgroundImageVisibleOnGround3D) {
    if (this.backgroundImageVisibleOnGround3D != backgroundImageVisibleOnGround3D) {
      this.backgroundImageVisibleOnGround3D = backgroundImageVisibleOnGround3D;
      this.propertyChangeSupport.firePropertyChange(Property.BACKGROUND_IMAGE_VISIBLE_ON_GROUND_3D.name(),
          !backgroundImageVisibleOnGround3D, backgroundImageVisibleOnGround3D);
    }
  }

  /**
   * Returns the sky color of this environment.
   */
  public int getSkyColor() {
    return this.skyColor;
  }

  /**
   * Sets the sky color of this environment and fires a <code>PropertyChangeEvent</code>.
   */
  public void setSkyColor(int skyColor) {
    if (skyColor != this.skyColor) {
      int oldSkyColor = this.skyColor;
      this.skyColor = skyColor;
      this.propertyChangeSupport.firePropertyChange(
          Property.SKY_COLOR.name(), oldSkyColor, skyColor);
    }
  }

  /**
   * Returns the sky texture of this environment.
   */
  public HomeTexture getSkyTexture() {
    return this.skyTexture;
  }

  /**
   * Sets the sky texture of this environment and fires a <code>PropertyChangeEvent</code>.
   */
  public void setSkyTexture(HomeTexture skyTexture) {
    if (skyTexture != this.skyTexture) {
      HomeTexture oldSkyTexture = this.skyTexture;
      this.skyTexture = skyTexture;
      this.propertyChangeSupport.firePropertyChange(
          Property.SKY_TEXTURE.name(), oldSkyTexture, skyTexture);
    }
  }

  /**
   * Returns the light color of this environment.
   */
  public int getLightColor() {
    return this.lightColor;
  }

  /**
   * Sets the color that lights this environment and fires a <code>PropertyChangeEvent</code>.
   */
  public void setLightColor(int lightColor) {
    if (lightColor != this.lightColor) {
      int oldLightColor = this.lightColor;
      this.lightColor = lightColor;
      this.propertyChangeSupport.firePropertyChange(
          Property.LIGHT_COLOR.name(), oldLightColor, lightColor);
    }
  }

  /**
   * Returns the color of ceiling lights.
   */
  public int getCeillingLightColor() {
    return this.ceilingLightColor;
  }

  /**
   * Sets the color of ceiling lights and fires a <code>PropertyChangeEvent</code>.
   */
  public void setCeillingLightColor(int ceilingLightColor) {
    if (ceilingLightColor != this.ceilingLightColor) {
      int oldCeilingLightColor = this.ceilingLightColor;
      this.ceilingLightColor = ceilingLightColor;
      this.propertyChangeSupport.firePropertyChange(
          Property.CEILING_LIGHT_COLOR.name(), oldCeilingLightColor, ceilingLightColor);
    }
  }

  /**
   * Returns the walls transparency alpha factor of this environment.
   */
  public float getWallsAlpha() {
    return this.wallsAlpha;
  }

  /**
   * Sets the walls transparency alpha of this environment and fires a <code>PropertyChangeEvent</code>.
   * @param wallsAlpha a value between 0 and 1, 0 meaning opaque and 1 invisible.
   */
  public void setWallsAlpha(float wallsAlpha) {
    if (wallsAlpha != this.wallsAlpha) {
      float oldWallsAlpha = this.wallsAlpha;
      this.wallsAlpha = wallsAlpha;
      this.propertyChangeSupport.firePropertyChange(
          Property.WALLS_ALPHA.name(), oldWallsAlpha, wallsAlpha);
    }
  }

  /**
   * Returns the drawing mode of this environment.
   */
  public DrawingMode getDrawingMode() {
    return this.drawingMode;
  }

  /**
   * Sets the drawing mode of this environment and fires a <code>PropertyChangeEvent</code>.
   */
  public void setDrawingMode(DrawingMode drawingMode) {
    if (drawingMode != this.drawingMode) {
      DrawingMode oldDrawingMode = this.drawingMode;
      this.drawingMode = drawingMode;
      this.propertyChangeSupport.firePropertyChange(
          Property.DRAWING_MODE.name(), oldDrawingMode, drawingMode);
    }
  }

  /**
   * Returns the size of subparts under home lights in this environment.
   * @return a size in centimeters or 0 if home lights don't illuminate home.
   * @since 3.7
   */
  public float getSubpartSizeUnderLight() {
    return this.subpartSizeUnderLight;
  }

  /**
   * Sets the size of subparts under home lights of this environment and fires a <code>PropertyChangeEvent</code>.
   * @since 3.7
   */
  public void setSubpartSizeUnderLight(float subpartSizeUnderLight) {
    if (subpartSizeUnderLight != this.subpartSizeUnderLight) {
      float oldSubpartWidthUnderLight = this.subpartSizeUnderLight;
      this.subpartSizeUnderLight = subpartSizeUnderLight;
      this.propertyChangeSupport.firePropertyChange(
          Property.SUBPART_SIZE_UNDER_LIGHT.name(), oldSubpartWidthUnderLight, subpartSizeUnderLight);
    }
  }

  /**
   * Returns whether all levels should be visible or not.
   */
  public boolean isAllLevelsVisible() {
    return this.allLevelsVisible;
  }

  /**
   * Sets whether all levels should be visible or not and fires a <code>PropertyChangeEvent</code>.
   */
  public void setAllLevelsVisible(boolean allLevelsVisible) {
    if (allLevelsVisible != this.allLevelsVisible) {
      this.allLevelsVisible = allLevelsVisible;
      this.propertyChangeSupport.firePropertyChange(
          Property.ALL_LEVELS_VISIBLE.name(), !allLevelsVisible, allLevelsVisible);
    }
  }

  /**
   * Returns the preferred photo width.
   * @since 2.0
   */
  public int getPhotoWidth() {
    return this.photoWidth;
  }

  /**
   * Sets the preferred photo width, and notifies
   * listeners of this change.
   * @since 2.0
   */
  public void setPhotoWidth(int photoWidth) {
    if (this.photoWidth != photoWidth) {
      int oldPhotoWidth = this.photoWidth;
      this.photoWidth = photoWidth;
      this.propertyChangeSupport.firePropertyChange(Property.PHOTO_WIDTH.name(),
          oldPhotoWidth, photoWidth);
    }
  }

  /**
   * Returns the preferred photo height.
   * @since 2.0
   */
  public int getPhotoHeight() {
    return this.photoHeight;
  }

  /**
   * Sets the preferred photo height, and notifies
   * listeners of this change.
   * @since 2.0
   */
  public void setPhotoHeight(int photoHeight) {
    if (this.photoHeight != photoHeight) {
      int oldPhotoHeight = this.photoHeight;
      this.photoHeight = photoHeight;
      this.propertyChangeSupport.firePropertyChange(Property.PHOTO_HEIGHT.name(),
          oldPhotoHeight, photoHeight);
    }
  }

  /**
   * Returns the preferred photo aspect ratio.
   * @since 2.0
   */
  public AspectRatio getPhotoAspectRatio() {
    return this.photoAspectRatio;
  }

  /**
   * Sets the preferred photo aspect ratio, and notifies
   * listeners of this change.
   * @since 2.0
   */
  public void setPhotoAspectRatio(AspectRatio photoAspectRatio) {
    if (this.photoAspectRatio != photoAspectRatio) {
      AspectRatio oldPhotoAspectRatio = this.photoAspectRatio;
      this.photoAspectRatio = photoAspectRatio;
      this.propertyChangeSupport.firePropertyChange(Property.PHOTO_ASPECT_RATIO.name(),
          oldPhotoAspectRatio, photoAspectRatio);
    }
  }

  /**
   * Returns the preferred photo quality.
   * @since 2.0
   */
  public int getPhotoQuality() {
    return this.photoQuality;
  }

  /**
   * Sets preferred photo quality, and notifies
   * listeners of this change.
   * @since 2.0
   */
  public void setPhotoQuality(int photoQuality) {
    if (this.photoQuality != photoQuality) {
      int oldPhotoQuality = this.photoQuality;
      this.photoQuality = photoQuality;
      this.propertyChangeSupport.firePropertyChange(Property.PHOTO_QUALITY.name(),
          oldPhotoQuality, photoQuality);
    }
  }

  /**
   * Returns the preferred video width.
   * @since 2.3
   */
  public int getVideoWidth() {
    return this.videoWidth;
  }

  /**
   * Sets the preferred video width, and notifies
   * listeners of this change.
   * @since 2.3
   */
  public void setVideoWidth(int videoWidth) {
    if (this.videoWidth != videoWidth) {
      int oldVideoWidth = this.videoWidth;
      this.videoWidth = videoWidth;
      this.propertyChangeSupport.firePropertyChange(Property.VIDEO_WIDTH.name(),
          oldVideoWidth, videoWidth);
    }
  }

  /**
   * Returns the preferred video height.
   * @since 2.3
   */
  public int getVideoHeight() {
    return Math.round(getVideoWidth() / getVideoAspectRatio().getValue());
  }

  /**
   * Returns the preferred video aspect ratio.
   * @since 2.3
   */
  public AspectRatio getVideoAspectRatio() {
    return this.videoAspectRatio;
  }

  /**
   * Sets the preferred video aspect ratio, and notifies
   * listeners of this change.
   * @since 2.3
   */
  public void setVideoAspectRatio(AspectRatio videoAspectRatio) {
    if (this.videoAspectRatio != videoAspectRatio) {
      if (videoAspectRatio.getValue() == null) {
        throw new IllegalArgumentException("Unsupported aspect ratio " + videoAspectRatio);
      }
      AspectRatio oldVideoAspectRatio = this.videoAspectRatio;
      this.videoAspectRatio = videoAspectRatio;
      this.propertyChangeSupport.firePropertyChange(Property.VIDEO_ASPECT_RATIO.name(),
          oldVideoAspectRatio, videoAspectRatio);
    }
  }

  /**
   * Returns preferred video quality.
   * @since 2.3
   */
  public int getVideoQuality() {
    return this.videoQuality;
  }

  /**
   * Sets the preferred video quality, and notifies
   * listeners of this change.
   * @since 2.3
   */
  public void setVideoQuality(int videoQuality) {
    if (this.videoQuality != videoQuality) {
      int oldVideoQuality = this.videoQuality;
      this.videoQuality = videoQuality;
      this.propertyChangeSupport.firePropertyChange(Property.VIDEO_QUALITY.name(),
          oldVideoQuality, videoQuality);
    }
  }

  /**
   * Returns the preferred speed of movements in videos in m/s.
   * @since 6.0
   */
  public float getVideoSpeed() {
    return this.videoSpeed;
  }

  /**
   * Sets the preferred speed of movements in videos in m/s.
   * @since 6.0
   */
  public void setVideoSpeed(float videoSpeed) {
    if (this.videoSpeed != videoSpeed) {
      float oldVideoSpeed = this.videoSpeed;
      this.videoSpeed = videoSpeed;
      this.propertyChangeSupport.firePropertyChange(Property.VIDEO_SPEED.name(),
          oldVideoSpeed, videoSpeed);
    }
  }

  /**
   * Returns the preferred video frame rate.
   * @since 2.3
   */
  public int getVideoFrameRate() {
    return this.videoFrameRate;
  }

  /**
   * Sets the preferred video frame rate, and notifies
   * listeners of this change.
   * @since 2.3
   */
  public void setVideoFrameRate(int videoFrameRate) {
    if (this.videoFrameRate != videoFrameRate) {
      int oldVideoFrameRate = this.videoFrameRate;
      this.videoFrameRate = videoFrameRate;
      this.propertyChangeSupport.firePropertyChange(Property.VIDEO_FRAME_RATE.name(),
          oldVideoFrameRate, videoFrameRate);
    }
  }

  /**
   * Returns the preferred video camera path.
   * @since 2.3
   */
  public List<Camera> getVideoCameraPath() {
    return Collections.unmodifiableList(this.cameraPath);
  }

  /**
   * Sets the preferred video camera path, and notifies
   * listeners of this change.
   * @since 2.3
   */
  public void setVideoCameraPath(List<Camera> cameraPath) {
    if (this.cameraPath != cameraPath) {
      List<Camera> oldCameraPath = this.cameraPath;
      if (cameraPath != null) {
        this.cameraPath = new ArrayList<Camera>(cameraPath);
      } else {
        this.cameraPath = Collections.emptyList();
      }
      this.propertyChangeSupport.firePropertyChange(Property.VIDEO_CAMERA_PATH.name(), oldCameraPath, cameraPath);
    }
  }

  /**
   * Returns a clone of this environment.
   * @since 2.3
   */
  @Override
  public HomeEnvironment clone() {
    HomeEnvironment clone = (HomeEnvironment)super.clone();
    clone.cameraPath = new ArrayList<Camera>(this.cameraPath.size());
    for (Camera camera : this.cameraPath) {
      clone.cameraPath.add(camera.clone());
    }
    clone.propertyChangeSupport = new PropertyChangeSupport(clone);
    return clone;
  }
}