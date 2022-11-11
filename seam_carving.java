package com.gradescope.photoeditor;

import java.awt.*;
import java.net.URL;

public class Picture extends SimplePicture
{
        /////////////////////////// Static Variables //////////////////////////////

        // Different axes available to flip a picture.
        public static final int HORIZONTAL = 1;
        public static final int VERTICAL = 2;
        public static final int FORWARD_DIAGONAL = 3;
        public static final int BACKWARD_DIAGONAL = 4;


        //////////////////////////// Constructors /////////////////////////////////

        /**
         * A constructor that takes no arguments.
         */
        public Picture () {
                super();
        }

        /**
         * Creates a Picture from the file name provided.
         *
         * @param fileName The name of the file to create the picture from.
         */
        public Picture(String fileName) {
                // Let the parent class handle this fileName.
                super(fileName);
        }

        /**
         * Creates a Picture from the width and height provided.
         *
         * @param width the width of the desired picture.
         * @param height the height of the desired picture.
         */
        public Picture(int width, int height) {
                // Let the parent class handle this width and height.
                super(width, height);
        }

        /**
         * Creates a copy of the Picture provided.
         *
         * @param pictureToCopy Picture to be copied.
         */
        public Picture (Picture pictureToCopy) {
                // Let the parent class do the copying.
                super(pictureToCopy);
        }

        /**
         * Creates a copy of the SimplePicture provided.
         *
         * @param pictureToCopy SimplePicture to be copied.
         */
        public Picture (SimplePicture pictureToCopy) {
                // Let the parent class do the copying.
                super(pictureToCopy);
        }

        /////////////////////////////// Methods ///////////////////////////////////

        //////////////////////////// Provided Methods /////////////////////////////////

        /**
         * Helper method to determine if a x and y coordinate is valid (within the image)
         *
         * @param ix is the x value that might be outside of the image
         * @param iy is the y value that might be outside of the image
         * @return true if the x and y values are within the image and false otherwise
         */
        @SuppressWarnings("unused")
        private boolean inImage(int ix, int iy) {
                return ix >= 0 && ix < this.getWidth() && iy >= 0
                                && iy < this.getHeight();
        }

        /**
         * @return A string with information about the picture, such as
         *      filename, height, and width.
         */
        public String toString() {
                String output = "Picture, filename = " + this.getFileName() + "," +
                " height = " + this.getHeight() + ", width = " + this.getWidth();
                return output;
        }
        /**
         * Equals method for two Picture objects.
         *
         * @param obj is an Object to compare to the current Picture object
         * @return true if obj is a Picture object with the same size as the
         *         original and with the same color at each Pixel
         */
        public boolean equals(Object obj) {
                if (!(obj instanceof Picture)) {
                        return false;
                }

                Picture p = (Picture) obj;
                // Check that the two pictures have the same dimensions.
                if ((p.getWidth() != this.getWidth()) ||
                                (p.getHeight() != this.getHeight())) {
                        return false;
                }

                // Check each pixel.
                for (int x = 0; x < this.getWidth(); x++) {
                        for(int y = 0; y < this.getHeight(); y++) {
                                if (!this.getPixel(x, y).equals(p.getPixel(x, y))) {
                                        return false;
                                }
                        }
                }

                return true;
        }

        /**
         * Helper method for loading a picture in the current directory.
         */
        protected static Picture loadPicture(String pictureName) {
                URL url = Picture.class.getResource(pictureName);
                return new Picture(url.getFile().replaceAll("%20", " "));
        }

        //////////////////////////// Debugging Methods /////////////////////////////////

        /**
         * Method to print out a table of the intensity for each Pixel in an image
         */
        public void printLuminosity(){
                int pictureHeight = this.getHeight();
                int pictureWidth = this.getWidth();
                System.out.println("Luminosity:");
                for(int y = 0; y < pictureHeight; y++) {
                        System.out.print("[");
                        for(int x = 0; x < pictureWidth; x++) {
                                System.out.print(this.luminosityOfPixel(x, y) + "\t");
                        }
                        System.out.println("]");
                }
        }
        /**
         * Method to print out a table of the energy for each Pixel in an image
         */
        public void printEnergy(){
                int pictureHeight = this.getHeight();
                int pictureWidth = this.getWidth();
                System.out.println("Energy:");
                for(int y = 0; y < pictureHeight; y++) {
                        System.out.print("[");
                        for(int x = 0; x < pictureWidth; x++) {
                                System.out.print(this.getEnergy(x, y) + "\t");
                        }
                        System.out.println("]");
                }
        }

        /**
         * Prints a two dimensional array of ints
         * @param array
         */
        public static void printArray(int[][] array) {
                int height = array.length;
                int width = array[0].length;
                for (int r = 0; r < width; r++) {
                        for (int c = 0; c < height; c++) {
                                System.out.print(array[c][r] + "\t");
                        }
                        System.out.println();
                }
        }

        /**
         * This method can be used like the other Picture methods, to create a
         * Picture that shows what Pixels are different between two Picture objects.
         *
         * @param picture2 is a Picture to compare the current Picture to
         * @return returns a new Picture with red pixels indicating differences between
         *                      the two Pictures
         */
        public Picture showDifferences(Picture picture2){
                Picture picOutput = new Picture(this);

                int pictureHeight = this.getHeight();
                int pictureWidth = this.getWidth();
                Color red = new Color(255, 0, 0);
                for(int x = 0; x < pictureWidth; x++) {
                        for(int y = 0; y < pictureHeight; y++) {
                                if (!this.getPixel(x, y).equals(picture2.getPixel(x, y))) {
                                        Pixel p = picOutput.getPixel(x, y);
                                        p.setColor(red);
                                }
                        }
                }
                return picOutput;
        }

        /**
         * This helper method is used to test the method fillTables. Specifically it
         * focuses on the 2D array "table"
         */
        public int[][] getTableAfterFillTables() {
                int height = this.getHeight();
                int width = this.getWidth();

                int[][] table = new int[width][height];
                int[][] parent = new int[width][height];
                this.fillTables(table, parent);
                return table;
        }

        /**
         * This helper method is used to test the method fillTables. Specifically it
         * focuses on the 2D array "parent"
         */
        public int[][] getParentAfterFillTables() {
                int height = this.getHeight();
                int width = this.getWidth();

                int[][] table = new int[width][height];
                int[][] parent = new int[width][height];
                this.fillTables(table, parent);
                return parent;
        }

        //////////////////////////// Grayscale Example /////////////////////////////////
        /*
         * Each of the methods below is constructive: in other words, each of the
         * methods below generates a new Picture, without permanently modifying the
         * original Picture.
         */

        /**
         * Returns a new Picture, which is the gray version of the current Picture (this)
         *
         * This is an example where all of the pixel-processing occurs within
         * the nested for loops (over the columns, x, and rows, y).
         *
         * @return A new Picture that is the grayscale version of this Picture.
         */
        public Picture grayscaleNoHelper() {
                Picture picOutput = new Picture(this);

                int pictureHeight = this.getHeight();
                int pictureWidth = this.getWidth();

                for (int x = 0; x < pictureWidth; x++) {
                        for (int y = 0; y < pictureHeight; y++) {

                                Pixel currentPixel = picOutput.getPixel(x, y);

                                Color c = currentPixel.getColor();
                                int redComponent = c.getRed();
                                int greenComponent = c.getGreen();
                                int blueComponent = c.getBlue();

                                int average = (redComponent + greenComponent + blueComponent) / 3;

                                currentPixel.setRed(average);
                                currentPixel.setGreen(average);
                                currentPixel.setBlue(average);
                        }
                }
                return picOutput;
        }


        /**
         * Converts the Picture into grayscale. Since any variation of gray
         *      is obtained by setting the red, green, and blue components to the same
         *      value, a Picture can be converted into its grayscale component
         *      by setting the red, green, and blue components of each pixel in the
         *      new picture to the same value: the average of the red, green, and blue
         *      components of the same pixel in the original.
         *
         * This example shows a more modular approach: grayscale uses a helper
         * named setPixelToGray; setPixelToGray, in turn, uses the helper averageOfRGB.
         *
         * @return A new Picture that is the grayscale version of this Picture.
         */
        public Picture grayscale() {
                Picture picOutput = new Picture(this);

                int pictureHeight = this.getHeight();
                int pictureWidth = this.getWidth();

                for(int x = 0; x < pictureWidth; x++) {
                        for(int y = 0; y < pictureHeight; y++) {
                                picOutput.setPixelToGray(x, y);
                        }
                }
                return picOutput;
        }

        /**
         * Helper method for grayscale() to set a pixel at (x, y) to be gray.
         *
         * @param x The x-coordinate of the pixel to be set to gray.
         * @param y The y-coordinate of the pixel to be set to gray.
         */
        private void setPixelToGray(int x, int y) {
                Pixel currentPixel = this.getPixel(x, y);
                int average = Picture.averageOfRGB(currentPixel.getColor());
                currentPixel.setRed(average);
                currentPixel.setGreen(average);
                currentPixel.setBlue(average);
        }
        /**
         * Helper method for grayscale() to calculate the
         * average value of red, green and blue.
         *
         * @param c is the Color to be averaged
         * @return The average of the red, green and blue values of this Color
         */
        private static int averageOfRGB(Color c) {
                int redComponent = c.getRed();
                int greenComponent = c.getGreen();
                int blueComponent = c.getBlue();

                // this uses integer division, which is what we want here
                // pixels always need to have integer values from 0 to 255 (inclusive)
                // for their red, green, and blue components:
                int average = (redComponent + greenComponent + blueComponent) / 3;
                return average;
        }

        //////////////////////////// Change Colors Menu /////////////////////////////////

        //////////////////////////// Negate /////////////////////////////////

        /**
         * Converts the Picture into its photonegative version. The photonegative
         *      version of an image is obtained by setting each of the red, green,
         *      and blue components of every pixel to a value that is 255 minus their
         *      current values.
         *
         * @return A new Picture that is the photonegative version of this Picture.
         */
        public Picture negate() {
               Picture picOutput = new Picture(this);
               int pictureHeight = this.getHeight();
               int pictureWidth = this.getWidth();
               for (int x = 0; x < pictureWidth; x++) {
                   for (int y = 0; y < pictureHeight; y++) {

                           Pixel currentPixel = picOutput.getPixel(x, y);

                           Color c = currentPixel.getColor();
                           int redComponent = c.getRed();
                           int greenComponent = c.getGreen();
                           int blueComponent = c.getBlue();

                           // set each color component to 255 minus the current value of respective component
                           currentPixel.setRed(255-redComponent);
                           currentPixel.setGreen(255-greenComponent);
                           currentPixel.setBlue(255-blueComponent);
                   }
               }
               return picOutput;
        }
        //////////////////////////// Lighten /////////////////////////////////

        /**
         * Creates an image that is lighter than the original image. The range of
         * each color component should be between 0 and 255 in the new image. The
         * alpha value should not be changed.
         *
         * @return A new Picture that has every color value of the Picture increased
         *         by the lightenAmount.
         */
        public Picture lighten(int lightenAmount) {
        	// feeds the lightenAmount to each RGB value into the helper method
        	return rgbValueHelper(lightenAmount, lightenAmount, lightenAmount);
        }
        
        /**
         * helper method that aids in carrying out methods like lighten, darken, addBlue, addRed, and addGreen
         * specifically, it takes in values for R, G, and B that should be added to the red, green, and blue
         * components of the image.
         * @param r
         * @param g
         * @param b
         * @return the final picture
         */
        public Picture rgbValueHelper(int r, int g, int b) {
        	Picture picOutput = new Picture(this);
        	int pictureHeight = this.getHeight();
            int pictureWidth = this.getWidth();
            for (int x = 0; x < pictureWidth; x++) {
                for (int y = 0; y < pictureHeight; y++) {

                        Pixel currentPixel = picOutput.getPixel(x, y);

                        Color c = currentPixel.getColor();
                        int redComponent = c.getRed();
                        int greenComponent = c.getGreen();
                        int blueComponent = c.getBlue();
                        // the use of Math.max and Math.min ensures that the RGB values stay between 0 and 255
                    	redComponent = Math.max(0, Math.min(255, c.getRed() + r));
                    	greenComponent = Math.max(0, Math.min(255, c.getGreen() + g));
                    	blueComponent = Math.max(0, Math.min(255, c.getBlue() + b));
                        currentPixel.setRed(redComponent);
                        currentPixel.setGreen(greenComponent);
                        currentPixel.setBlue(blueComponent);
                }
            }
            return picOutput;
        }

        //////////////////////////// Darken /////////////////////////////////

        /**
         * Creates an image that is darker than the original image.The range of
         * each color component should be between 0 and 255 in the new image. The
         * alpha value should not be changed.
         *
         * @return A new Picture that has every color value of the Picture decreased
         *         by the darkenAmount.
         */
        public Picture darken(int darkenAmount) {
        	// feeds a negative amount to each RGB value into the helper method
        	return rgbValueHelper(-darkenAmount, -darkenAmount, -darkenAmount);
        }

        //////////////////////////// Add[Blue,Green,Red] /////////////////////////////////

        /**
         * Creates an image where the blue value has been increased by amount.The range of
         * each color component should be between 0 and 255 in the new image. The
         * alpha value should not be changed.
         *
         * @return A new Picture that has every blue value of the Picture increased
         *         by amount.
         */
        public Picture addBlue(int amount) {
        	// only feeds amount to the blue component of the helper method 
        	return rgbValueHelper(0, 0, amount);
        }

        /**
         * Creates an image where the red value has been increased by amount. The range of
         * each color component should be between 0 and 255 in the new image. The
         * alpha value should not be changed.
         *
         * @return A new Picture that has every red value of the Picture increased
         *         by amount.
         */
        public Picture addRed(int amount) {
        	// only feeds amount to the red component of the helper method
        	return rgbValueHelper(amount, 0, 0);
        }

        /**
         * Creates an image where the green value has been increased by amount. The range of
         * each color component should be between 0 and 255 in the new image. The
         * alpha value should not be changed.
         *
         * @return A new Picture that has every green value of the Picture increased
         *         by amount.
         */
        public Picture addGreen(int amount) {
        	//only feeds amount to the green component of the helper method
        	return rgbValueHelper(0, amount, 0);
        }

        //////////////////////////// Rotate Right /////////////////////////////////

        /**
         * Returns a new picture where the Picture is rotated to the right by 90
         * degrees. If the picture was originally 50 Pixels by 70 Pixels, the new
         * Picture should be 70 Pixels by 50 Pixels.
         *
         * @return a new Picture rotated right by 90 degrees
         */
        public Picture rotateRight() {
        	int origHeight = this.getHeight();
        	int origWidth = this.getWidth();
        	Picture finalPic = new Picture(origHeight, origWidth);
        	
        	for (int origX = 0; origX < origWidth; origX++) {
        	    for (int origY = 0; origY < origHeight; origY++) {
        	        Pixel origPixel = this.getPixel(origX, origY);
        	        int finalX = origHeight - origY - 1;
        	        int finalY = origX;
        	        finalPic.getPixel(finalX, finalY).setColor(origPixel.getColor());
        	     }
        	 }
        	    
        	 return finalPic;
        }

        //////////////////////////// Seam Carving Section /////////////////////////////////

        //////////////////////////// Luminosity /////////////////////////////////
        /**
         * Returns a Picture of a version of grayscale using luminosity instead
         * of a direct average. The Picture should be converted into its luminosity
         * version by setting the red, green, and blue components of each pixel in
         * the new picture to the same value: the luminosity of the red, green, and
         * blue components of the same pixel in the original. Where luminosity =
         * 0.21 * redness + 0.72 * greenness + 0.07 * blueness
         *
         * @return A new Picture that is the luminosity version of this Picture.
         */
        public Picture luminosity(){
        	Picture picOutput = new Picture(this);
        	int pictureHeight = this.getHeight();
            int pictureWidth = this.getWidth();
            for (int x = 0; x < pictureWidth; x++) {
                for (int y = 0; y < pictureHeight; y++) {

                        Pixel currentPixel = picOutput.getPixel(x, y);
                        int lumin = luminosityOfPixel(x,y);

                        // sets the pixel's R, G, and B values to the luminosity values for that pixel
                        currentPixel.setRed(lumin);
                        currentPixel.setGreen(lumin);
                        currentPixel.setBlue(lumin);
                }
            }
            return picOutput;
        }


        /**
         * Helper method for luminosity() to calculate the
         * luminosity of a pixel at (x,y).
         *
         * @param x  the x-coordinate of the pixel
         * @param y  the y-coordinate of the pixel
         * @return The luminosity of that pixel
         */
        private int luminosityOfPixel(int x, int y) {
        	// uses the provided formula to calculate the luminosity of the pixel at coordinates x, y.
        	Pixel origPixel = this.getPixel(x, y);
        	return (int)(0.21 * origPixel.getRed() + 0.72 * origPixel.getGreen() + 0.07 * origPixel.getBlue());
        }

        //////////////////////////// Energy /////////////////////////////////

        /**
         * Returns a Picture into a version of the energy of the Picture
         *
         * @return A new Picture that is the energy version of this Picture.
         */
        public Picture energy(){
        	Picture picOutput = new Picture(this);
        	int pictureHeight = this.getHeight();
            int pictureWidth = this.getWidth();
            for (int x = 0; x < pictureWidth; x++) {
                for (int y = 0; y < pictureHeight; y++) {
                    Pixel currentPixel = picOutput.getPixel(x, y);
                    int energy = this.getEnergy(x, y);
                    // sets the R, G, and B values to the energy of the image.
                    currentPixel.setRed(energy);
                    currentPixel.setGreen(energy);
                    currentPixel.setBlue(energy);
                }
            }
            return picOutput;
        }

        /**
         * Helper method for energy() to calculate the
         * energy of a Pixel.
         *
         * @param x is the x value of the Pixel to be evaluated
         * @param y is the y value of the Pixel to be evaluated
         * @return The energy of this Pixel
         */
        private int getEnergy(int x, int y) {
        	// the energy at each pixel is the sum of the absolute values (Math.abs) of two derivatives: 
        	// the horizontal and vertical rates of change in luminosity
        	int luminTwoX = 0, luminTwoY = 0;
        	int lumin = luminosityOfPixel(x,y);
            if (x == this.getWidth()-1) {
            	luminTwoX = luminosityOfPixel(x-1, y); 
            }
            else {
            	luminTwoX = luminosityOfPixel(x+1, y);
            }
            if (y == this.getHeight()-1) {
            	luminTwoY = luminosityOfPixel(x, y-1);
            }
            else {
            	luminTwoY = luminosityOfPixel(x, y+1);
            }
            return Math.abs(luminTwoX - lumin) + Math.abs(luminTwoY - lumin);
        }


        //////////////////////////// Compute Seam /////////////////////////////////

        /**
         * helper method computeSeam returns an int array with the x-coordinates
         * (columns) of the lowest-energy seam running from the top row to the
         * bottom row.
         *
         * We would normally make this method private, but it is public to enable us
         * to test it. See the course assignment for additional details.
         */
        public int[] computeSeam() {
                int height = this.getHeight();
                int width = this.getWidth();
                int[] seam = new int[height];
                int[][] table = new int[width][height];
                int[][] parent = new int[width][height];
                this.fillTables(table, parent);
                
                int minima = table[0][height-1];
                int minColumn = 0;

                for (int row = height-1; row >= 0; row--) {
                    for (int col = 0; col < width; col++) {
                    	if(minima > table[col][row]) {
                    		minima = table[col][row];
                    		minColumn = col;
                    	}
                    }
                    seam[row] = minColumn;
                }
                // update seam values with values at the minColumn indices within parent 2D array
                for (int row = height - 1; row > 0; row--) {
                	seam[row-1]= parent[seam[row]][row];
                }
                return seam;
        }
        /**
         * helper method fillTables is passed two, 2D arrays and fills them with the
         * "table" and "parent" contents as described in the assignment.
         *
         * See the course assignment for additional details.
         */
        private void fillTables(int[][] table, int[][] parent) {
        	for (int y = 0; y < this.getHeight(); y++) {
            	for (int x = 0; x < this.getWidth(); x++) {
            		if (y == 0) {
            			// first row of table is the first row of the energies themselves
            			table[x][y] = this.getEnergy(x,0);
            			// first row of parent set to 0 since there is no row above the first one
                    	parent[x][y] = 0;
            		}
            		else {
            			// minTableValue keeps track of the minimum table value in the row above row y (spanning from x-1 col to x+1)
            			int minTableValue = 0;
            			if (x == 0) {
            				minTableValue = Math.min(table[x][y-1], table[x+1][y-1]);
    					}
    					else if (x == this.getWidth()-1) {
    						minTableValue = Math.min(table[x-1][y-1], table[x][y-1]);
    					}
    					else {
    						minTableValue = Math.min(table[x-1][y-1], Math.min(table[x][y-1], table[x+1][y-1]));	
    					}
            			table[x][y] = this.getEnergy(x,y) + minTableValue;
            			parent[x][y] = getColumn(table, x, y, minTableValue);
            		}
				}
            }
        }
        
        /**
         * 
         * Helper function to extract the column number associated with the minimum value in the table given a certain row
         * 
         * @param table
         * @param x
         * @param y
         * @param minTableValue
         * @return column number that holds the minimum table value (above-left, above, and above-right if possible)
         */
        private int getColumn(int [][] table, int x, int y, int minTableValue) {
        	int columnNumber = -1;
        	// every cell being searched has a valid cell directly above it. if the value at that cell is the minimum, update
        	// the columnNumber variable from -1 to the respective column value.
    		if (minTableValue == table[x][y-1]) {
				columnNumber = x;
    		}
    		// if the columnNumber variable hasn't been updated and the current column is 0, the only other option is x+1
        	if (x == 0 && columnNumber == -1) {
				columnNumber = x+1;
        	}
        	// similar to the above condition, the only other option is x-1 if the variable hasn't been updated
        	else if (x == table.length-1 && columnNumber == -1) {
    			columnNumber = x-1;
        	}
        	// else if the current column is neither the first nor the last column and the variable hasn't been updated,
        	// the columnNumber variable can only either be x+1 or x-1.
        	else if (columnNumber == -1){
				if (minTableValue == table[x-1][y-1] ) {
					columnNumber = x-1;
        		}
				else {
					columnNumber = x+1;
        		}
        	}
        	return columnNumber;
        }

        //////////////////////////// Show Seam /////////////////////////////////

        /**
         * Returns a new image, with the lowest cost seam shown in red. The lowest
         * cost seam is calculated by calling computeSeam()
         *
         * @return a new Picture
         */
        public Picture showSeam(){
        	int[] seam = this.computeSeam();
        	Picture newPicture = new Picture(this);
            for (int y = 0; y < this.getHeight(); y++) {
            	for (int x = 0; x < this.getWidth(); x++) {
            		if (x == seam[y]) {
            			// show the seam when the column matches the respective value in the seam array (in red) 
                        Pixel pixel = newPicture.getPixel(x, y);
                        pixel.setRed(255);
                        pixel.setGreen(0);
                        pixel.setBlue(0);
                    }
              }
            }
            return newPicture;
        }

        //////////////////////////// Carving (2 methods) /////////////////////////////////

        /**
         * Returns a new picture, where the seam identified by calling computeSeam() is
         * removed. The resulting image should be the same height as the original
         * but have a width that is one smaller than the original.
         */
        public Picture carve(){
        	Picture originalPic = new Picture(this.showSeam());
        	int newWidth = originalPic.getWidth()-1;
        	int newHeight = originalPic.getHeight();	
        	int pixelPosition = 0;
        	Picture newPicture = new Picture(newWidth, newHeight);
        	for (int y = 0; y < originalPic.getHeight(); y++) {
        		for (int x = 0; x < originalPic.getWidth(); x++) {
        			Pixel origPix = originalPic.getPixel(x, y);
        			Pixel newPix = newPicture.getPixel(pixelPosition, y);
        			Color c = origPix.getColor();
        			
        			int red = c.getRed();
        			int green = c.getGreen();
        			int blue = c.getBlue();
        			
        			// if it's not red, include it in the picture
        			if (!(red == 255 && green == 0 && blue == 0)) {
        				newPix.setRed(red);
        				newPix.setGreen(green);
        				newPix.setBlue(blue);
        				pixelPosition++;
        			}
        			
        		}
        		pixelPosition = 0;
        	}
            return newPicture;
        }

        /**
         * This returns a new Picture that has a number of seams removed.
         *
         * If the input is greater than the width of the Picture, first print an error using
         * System.err instead of System.out, then return null. Here is the error message:
         *
         * System.err.println("Cannot call carveMany with argument " + numSeams + " on image of width " + this.getWidth());
         *
         * @param numSeams is the number of times that carve should be called
         * @return a new picture with numSeams removed
         */
        public Picture carveMany(int numSeams){
        	Picture pic = new Picture(this);
        	if (numSeams > this.getWidth()) {
        		System.err.println("Cannot call carveMany with argument " + numSeams + " on image of width " + this.getWidth());
        	}
        	else {
        		// loops over the numSeams number of times 
        		for (int i = 0; i < numSeams; i++) {
        			pic = pic.carve();
        		}
        	}
        	return pic;
        }


        //////////////////////////// Extra Credit /////////////////////////////////

        /**
         * @param x x-coordinate of the pixel currently selected.
         * @param y y-coordinate of the pixel currently selected.
         * @param background Picture to use as the background.
         * @param threshold Threshold within which to replace pixels.
         *
         * @return A new Picture where all the pixels in the original Picture,
         *      which differ from the currently selected pixel within the provided
         *      threshold (in terms of color distance), are replaced with the
         *      corresponding pixels in the background picture provided.
         *
         *      If the two Pictures are of different dimensions, the new Picture will
         *      have length equal to the smallest of the two Pictures being combined,
         *      and height equal to the smallest of the two Pictures being combined.
         *      In this case, the Pictures are combined as if they were aligned at
         *      the top left corner (0, 0).
         */
        public Picture chromaKey(int xRef, int yRef, Picture background, int threshold) {
        	int width = Math.min(this.getWidth(), background.getWidth());
        	int height = Math.min(this.getHeight(), background.getHeight());
        	Picture newPic = new Picture(width, height);
        	Color c = this.getPixel(xRef, yRef).getColor();
        	
        	// resets the pixel to the background pixel in the event that the original pixel is similar
        	for (int x = 0; x < width; x++) {
        		for (int y = 0; y < height; y++) {
        			Color originalColor = this.getPixel(x, y).getColor();
        			Color backgroundColor = background.getPixel(x, y).getColor();
        			newPic.resetPixel(threshold, x, y, c, originalColor, backgroundColor);
        		}
        	}
            return newPic;
        }
        
        /**
         * Helper method for chromaKey that resets the color of a given pixel if its color distance 
         * is less than the threshold value.
         * 
         * @param threshold
         * @param x
         * @param y
         * @param c
         * @param origColor
         * @param bgColor
         */
        private void resetPixel(int threshold, int x, int y, Color c, Color origColor, Color bgColor) {
            if ((int) Pixel.colorDistance(c, origColor) < threshold) {
            	this.getPixel(x, y).setColor(bgColor);
            } 
            else {
            	this.getPixel(x, y).setColor(origColor);
            }
        }

        //////////////////////////// Flip /////////////////////////////////

        /**
         * Flips this Picture about the given axis. The axis can be one of
         *      four static integer constants:
         *
         *      (a) Picture.HORIZONTAL: The picture should be flipped about
         *              a horizontal axis passing through the center of the picture.
         *      (b) Picture.VERTICAL: The picture should be flipped about
         *              a vertical axis passing through the center of the picture.
         *      (c) Picture.FORWARD_DIAGONAL: The picture should be flipped about
         *              the line that passes through the southwest corner of the
         *              picture and that extsends at 45deg. to the northeast
         *      (d) Picture.BACKWARD_DIAGONAL: The picture should be flipped about
         *              an axis that passes through the north-west corner and extends
         *              at a 45deg angle to the southeast
         *
         * If the input is not one of these static variables, print an error using
         * System.err (instead of System.out):
         *                              System.err.println("Invalid flip request");
         *   ... and then return null.
         *
         *
         * @param axis Axis about which to flip the Picture provided.
         *
         * @return A new Picture flipped about the axis provided.
         */
        public Picture flip(int axis) {
        	// invokes different functions depending on what axis is picked
        	if (axis == HORIZONTAL) {
        		return this.flipHoriz();
        	}
        	else if (axis == VERTICAL) {
        		return this.flipVert();
        	}
        	else if (axis == FORWARD_DIAGONAL) {
        		return this.flipForwardDiag();
        	}
        	else if (axis == BACKWARD_DIAGONAL) {
        		return this.flipBackwardDiag();
        	}
        	// prints error in the event that the axis isn't the one above 
        	System.err.println("Invalid flip request");
        	return null;
        }
        
        private void swap(int x, int y, int i, int j) {
        	Pixel p1 = this.getPixel(x, y);
        	Pixel p2 = this.getPixel(i, j);
        	Color temp = p1.getColor();
        	p1.setColor(p2.getColor());
        	p2.setColor(temp);
        }
        
        /**
         * method for flipping the image over the horizontal axis
         * @return
         */
        private Picture flipHoriz() {
        	// swaps the top and bottom pixels until the middle is reached
        	Picture newPic = new Picture(this);
        	int height = this.getHeight();
	       	int width = this.getWidth();
	       	for(int x = 0; x < width; x++) {
	       		for (int y = 0; y < height/2; y++) {
	       			newPic.swap(x, y, x, height-1-y);
	       		}
	       	}
	       	return newPic;
        }
        
        /**
         * method for flipping the image over the vertical axis
         * @return
         */
        private Picture flipVert() {
        	return this.flipHoriz().rotateRight().rotateRight();
        }
        
        /**
         * method for flipping the image over SW->NE diagonal axis
         * @return
         */
        private Picture flipForwardDiag() {
        	return this.rotateRight().flipHoriz();
        }
        
        /**
         * method for flipping the image over the NW->SE axis
         * @return
         */
        private Picture flipBackwardDiag() {
        	return this.rotateRight().flipHoriz().rotateRight().rotateRight();
        }

        //////////////////////////// Show Edges /////////////////////////////////

        /**
         * @param threshold
         *            Threshold to use to determine the presence of edges.
         *
         * @return A new Picture that contains only the edges of this Picture. For
         *         each pixel, we separately consider the color distance between
         *         that pixel and the one pixel to its left, and also the color
         *         distance between that pixel and the one pixel to the north, where
         *         applicable. As an example, we would compare the pixel at (3, 4)
         *         with the pixels at (3, 3) and the pixels at (2, 4). Also, since
         *         the pixel at (0, 4) only has a pixel to its north, we would only
         *         compare it to that pixel. If either of the color distances is
         *         larger than the provided color threshold, it is set to black
         *         (with an alpha of 255); otherwise, the pixel is set to white
         *         (with an alpha of 255). The pixel at (0, 0) will always be set to
         *         white.
         */
        public Picture showEdges(int threshold) {
        	int width = this.getWidth();
       	 	int height = this.getHeight();
       	 	int distNorth = 0;
       	 	int distWest = 0;
       	 	Picture newPic = new Picture(width, height);
       	 	for (int x = 0; x < width; x++) {
       	 		for (int y = 0; y < height; y++) {
       	 			Pixel newPix = newPic.getPixel(x, y);
	       	 		Pixel p = this.getPixel(x, y);
	            	Pixel northPix = this.getValidPixel(x, y - 1);
	            	Pixel westPix = this.getValidPixel(x - 1, y);
	            	// if the north pixel exists, calculate the color distance 
	            	if (northPix != null) {
	            		distNorth = (int) Pixel.colorDistance(p.getColor(), northPix.getColor());
	            	}
	            	// if the west pixel exists, calculate the color distance
	            	if (westPix != null) {
	            		distWest = (int) Pixel.colorDistance(p.getColor(), westPix.getColor());
	            	}
	            	// if the north or west distance is greater than the threshold, set the new pixel's 
	            	// color to black, else white 
	            	if (distNorth > threshold || distWest > threshold)
       	 			{
       	 				newPix.setColor(new Color (0, 0, 0, 255)); 
       	 			}
       	 			else
       	 			{
       	 				newPix.setColor(new Color(255, 255, 255, 255)); 
       	 			}
       	 		}
       	 		// reset the distances to 0 
       	 		distNorth = 0;
       	 		distWest = 0;
       	 	}
       	 	return newPic;
       	 }

        /**
         * Helper method for showEdges; returns the pixel at said location given that its location is valid.
         * If the location is invalid, the method will return a null.
         * @param x
         * @param y
         * @return 
         */
     	private Pixel getValidPixel(int x, int y) {
     		if (x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight()) {
     			return this.getPixel(x, y);
     		}
     		return null;
     	}
        
        //////////////////////////////// Blur //////////////////////////////////

        /**
         * Blurs this Picture. To achieve this, the algorithm takes a pixel, and
         * sets it to the average value of all the pixels in a square of side (2 *
         * blurThreshold) + 1, centered at that pixel. For example, if blurThreshold
         * is 2, and the current pixel is at location (8, 10), then we will consider
         * the pixels in a 5 by 5 square that has corners at pixels (6, 8), (10, 8),
         * (6, 12), and (10, 12). If there are not enough pixels available -- if the
         * pixel is at the edge, for example, or if the threshold is larger than the
         * image -- then the missing pixels are ignored, and the average is taken
         * only of the pixels available.
         *
         * The red, blue, green and alpha values should each be averaged separately.
         *
         * @param blurThreshold
         *            Size of the blurring square around the pixel.
         *
         * @return A new Picture that is the blurred version of this Picture, using
         *         a blurring square of size (2 * threshold) + 1.
         */
        public Picture blur(int blurThreshold) {
                // TODO: Write blur (Extra Credit)
                return null;
        }

        //////////////////////////////// Paint Bucket //////////////////////////////////

        /**
         * @param x x-coordinate of the pixel currently selected.
         * @param y y-coordinate of the pixel currently selected.
         * @param threshold Threshold within which to delete pixels.
         * @param newColor New color to color pixels.
         *
         * @return A new Picture where all the pixels connected to the currently
         *      selected pixel, and which differ from the selected pixel within the
         *      provided threshold (in terms of color distance), are colored with
         *      the new color provided.
         */
        public Picture paintBucket(int x, int y, int threshold, Color newColor) {
                // TODO: Write paintBucket (Extra Credit)
                return null;
        }

        //////////////////////////////// Main Method //////////////////////////////////

        public static void main(String[] args) {
        		Picture pic = Picture.loadPicture("kube.jpeg");
        		pic.carveMany(200).show();
        }

} // End of Picture class
