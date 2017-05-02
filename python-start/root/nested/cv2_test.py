import numpy
import matplotlib
import cv2

img = cv2.imread('C:\\dev\\bak\\files\\personal\\train\\python\\watch.jpg', cv2.IMREAD_GRAYSCALE)
cv2.imshow('image', img)
cv2.waitKey(0)
cv2.destroyAllWindows()
