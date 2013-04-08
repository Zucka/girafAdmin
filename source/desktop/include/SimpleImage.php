<?php
 
/*
* File: SimpleImage.php
* Author: Simon Jarvis
* Copyright: 2006 Simon Jarvis
* Date: 08/11/06
* Link: http://www.white-hat-web-design.co.uk/articles/php-image-resizing.php
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details:
* http://www.gnu.org/licenses/gpl.html
*
*/
 
class SimpleImage {
 
   var $image;
   var $image_type;
 
   function load($filename) {
 
      $image_info = getimagesize($filename);
      $this->image_type = $image_info[2];
      if( $this->image_type == IMAGETYPE_JPEG ) {
 
         $this->image = imagecreatefromjpeg($filename);
      } elseif( $this->image_type == IMAGETYPE_GIF ) {
 
         $this->image = imagecreatefromgif($filename);
      } elseif( $this->image_type == IMAGETYPE_PNG ) {
 
         $this->image = imagecreatefrompng($filename);
      }
   }
   function loadFromString($filename){
		$this->image = imagecreatefromstring($filename);
   }
   function save($filename, $image_type=IMAGETYPE_JPEG, $compression=75, $permissions=null) {
 
      if( $image_type == IMAGETYPE_JPEG ) {
         imagejpeg($this->image,$filename,$compression);
      } elseif( $image_type == IMAGETYPE_GIF ) {
 
         imagegif($this->image,$filename);
      } elseif( $image_type == IMAGETYPE_PNG ) {
 
         imagepng($this->image,$filename);
      }
      if( $permissions != null) {
 
         chmod($filename,$permissions);
      }
   }
   function output($image_type=IMAGETYPE_JPEG) {
 
      if( $image_type == IMAGETYPE_JPEG ) {
         imagejpeg($this->image);
      } elseif( $image_type == IMAGETYPE_GIF ) {
 
         imagegif($this->image);
      } elseif( $image_type == IMAGETYPE_PNG ) {
 
         imagepng($this->image);
      }
   }
   function getWidth() {
 
      return imagesx($this->image);
   }
   function getHeight() {
 
      return imagesy($this->image);
   }
   function resizeToHeight($height) {
 
      $ratio = $height / $this->getHeight();
      $width = $this->getWidth() * $ratio;
      $this->resize($width,$height);
   }
 
   function resizeToWidth($width) {
      $ratio = $width / $this->getWidth();
      $height = $this->getheight() * $ratio;
      $this->resize($width,$height);
   }
 
   function scale($scale) {
      $width = $this->getWidth() * $scale/100;
      $height = $this->getHeight() * $scale/100;
      $this->resize($width,$height);
   }
 
   function resize($width,$height) {
      $new_image = imagecreatetruecolor($width, $height);
	  //Out Commented - Not sure if edit is needed. If not, this is just waste code.
	  //$white_image = imagecolorallocate($new_image, 255, 255, 255); //Change image color to: White (RGB format)
	  //imagefill($new_image,0,0,$white_image);
      imagecopyresampled($new_image, $this->image, 0, 0, 0, 0, $width, $height, $this->getWidth(), $this->getHeight());
	  
      $this->image = $new_image;
   }
   
   function resizeCordsColor($width,$height,$x1,$y1,$x2,$y2,$red,$green,$blue) {
      $new_image = imagecreatetruecolor($width, $height);
	  $white_image = imagecolorallocate($new_image, $red, $green, $blue); //Change image color to: White (RGB format)
	  imagefill($new_image,0,0,$white_image);
      imagecopyresampled($new_image, $this->image, 0, 0, $x1, $y1, $width, $height, $x2-$x1, $y2-$y1);
	  
      $this->image = $new_image;
   }
   
   function resizeFillColor($newWidth,$newHeight,$red,$green,$blue){
		$new_image = imagecreatetruecolor($newWidth, $newHeight);
		$white_image = imagecolorallocate($new_image, $red, $green, $blue); //Change image color to: White (RGB format)
		imagefill($new_image,0,0,$white_image);
	  
		// Calculate the start center area of the image
		$ratio = $this->getWidth()/$this->getHeight();
		$newRatio = $newWidth/$newHeight;
		
		if($ratio > $newRatio)
		{
		//Full width
		$sizeNewWidth = $newWidth;
		$sizeNewHeight = $newWidth / $ratio;		
		$dst_x = 0;
		$dst_y = round(abs($newHeight - $sizeNewHeight)/2);
		}
		else
		{
		//Full height
		$sizeNewWidth = $newHeight * $ratio;
		$sizeNewHeight = $newHeight;
		$dst_x = round(abs($newWidth - $sizeNewWidth)/2);
		$dst_y = 0;
		}		
		
		imagecopyresampled($new_image, $this->image, $dst_x, $dst_y, 0, 0, $sizeNewWidth, $sizeNewHeight, $this->getWidth(), $this->getHeight());
		$this->image = $new_image;
		
   }
 
}

	function removeImages($filepathBig,$filepathMed,$filepathThumb){
		$tjek1 = unlink($filepathBig);
		$tjek2 = unlink($filepathMed);
		$tjek3 = unlink($filepathThumb);
		echo "Big: ". $tjek1." Med: ". $tjek2." Thumb: " . $tjek3;
	}
?>