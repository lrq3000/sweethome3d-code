<?php 
  /*
   * writeData.php 21 sept 2023
   *
   * Sweet Home 3D, Copyright (c) 2023 Emmanuel PUYBARET / eTeks <info@eteks.com>
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
   
  // Saves the posted data in the file name in "path" parameter
  $dataDir = "data";
  mkdir($dataDir);
  $dataFile = $dataDir."/".$_GET['path']; 

  $in = fopen("php://input", "rb");
  $out = fopen($dataFile, 'w');
  while (!feof($in)) {
    fwrite($out, fread($in, 8192));
  }
  fclose($in);
  fclose($out);
?>