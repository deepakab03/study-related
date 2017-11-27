package com.deepak.questions.int_q.tdme;

/**
 * A large package can hold five items, while the small packages can hold only one item. All items must be placed in
 * packages and used packages have to be filled up completely.
 * <p>
 * Write a function that calculates he minimum number of packages needed to hold a given number of items. If it's not
 * possible to meet the requirements, return -1
 * <p>
 * For example if we have 16 items, 2 large and 10 small packages, the function should return 8 (2 large packages + 6
 * small packages)
 * 
 * @author Deepak Abraham
 */
public class LargePackageSmallPackage {

    private static int CAPACITY_LARGE_BAG = 5;
    private static int CAPACITY_SMALL_BAG = 1;
    
    public int minimalNumPkges(int numItems, int givenLargePkgs, int givenSmallPkgs) {
        int reqdPkgs = 0;
        int numLarge = 0;
        int numSmall = 0;
        boolean found = false;
        
        while (givenLargePkgs > 0) {
            numLarge++;
            int largeCapacity = numLarge * CAPACITY_LARGE_BAG;
            if (largeCapacity == numItems) {
                reqdPkgs = numLarge;
                found = true;
                break;
            }
            if (largeCapacity > numItems) {
                numLarge--;
                break;
            } 
            
            givenLargePkgs--;
        }
        if (!found) {
            int obtainedCapacity = numLarge * CAPACITY_LARGE_BAG;
            
            while (givenSmallPkgs > 0) {
                numSmall++;
                int smallCapacity = numSmall * CAPACITY_SMALL_BAG;
                if (obtainedCapacity + smallCapacity == numItems) {
                    reqdPkgs = numLarge + numSmall;
                    found = true;
                    break;
                }
                
                givenSmallPkgs--;
            }
        }
        
        return found ? reqdPkgs : -1;
    }
    
    /**
     * Starting from the max number of given packages and going downwards
     * @param numItems
     * @param givenLargePkgs
     * @param givenSmallPkgs
     * @return
     */
    public int minimalNumPkges2(int numItems, int givenLargePkgs, int givenSmallPkgs) {
        int reqdPkgs = 0;
        int numLarge = givenLargePkgs;
        int numSmall = 0;
        boolean found = false;
        
        while (numLarge > 0) {
            int largeCapacity = numLarge * CAPACITY_LARGE_BAG;
            if (largeCapacity == numItems) {
                reqdPkgs = numLarge;
                found = true;
                break;
            } else if (largeCapacity > numItems) {
                numLarge--;
            } if (largeCapacity < numItems) {
               break;
            } 
        }
        if (!found) {
            int obtainedCapacity = numLarge * CAPACITY_LARGE_BAG;
            
            while (givenSmallPkgs > 0) {
                numSmall++;
                int smallCapacity = numSmall * CAPACITY_SMALL_BAG;
                if (obtainedCapacity + smallCapacity == numItems) {
                    reqdPkgs = numLarge + numSmall;
                    found = true;
                    break;
                }
                
                givenSmallPkgs--;
            }
        }
        
        return found ? reqdPkgs : -1;
    }
    
    public int minimalNumPkges3(int numItems, int givenLargePkgs, int givenSmallPkgs) {
        int reqdPkgs = 0;
        int numLarge = givenLargePkgs;
        int numSmall = 0;
        boolean found = false;
        
        while (numLarge > 0) {
            int largeCapacity = numLarge * CAPACITY_LARGE_BAG;
            if (largeCapacity == numItems) {
                reqdPkgs = numLarge;
                found = true;
                break;
            } else if (largeCapacity > numItems) {
                numLarge--;
            } if (largeCapacity < numItems) {
               break;
            } 
        }
        if (!found) {
            int obtainedCapacity = numLarge * CAPACITY_LARGE_BAG;
            
            while (givenSmallPkgs > 0) {
                numSmall++;
                int smallCapacity = numSmall * CAPACITY_SMALL_BAG;
                if (obtainedCapacity + smallCapacity == numItems) {
                    reqdPkgs = numLarge + numSmall;
                    found = true;
                    break;
                }
                
                givenSmallPkgs--;
            }
        }
        
        return found ? reqdPkgs : -1;
    }
    
    public  int minimalNumberOfPackagesCh1(int items, int availableLargePackages, int availableSmallPackages) {
        int units = 0;
        int biggerUnits = 0;
        int smallPkt = 0;
        int result = -1;
        if (availableLargePackages != 0) {
            units = 5 * availableLargePackages;
            if ( units < items) {
                smallPkt = items - units;
                result = smallPkt + availableLargePackages;
            }
            else if( units == items) {
                result = availableLargePackages;
            }
            else if(units > items) {
                biggerUnits = items/5;
                smallPkt = items%5;
                result = biggerUnits + smallPkt;
            }
          } 
        else if(availableLargePackages == 0) {
            result = items;
        }

        return result;
    }
    
    public  int minimalNumberOfPackagesCh2(int items, int availableLargePackages, int availableSmallPackages) {   
        int units = 0;
        int biggerUnits = 0;
        int smallPkt = 0;
        int result = -1;
        if (availableLargePackages != 0 ) {
            units = 5 * availableLargePackages;
            if(availableSmallPackages != 0) {
                if ( units < items) {
                    smallPkt = items - units;
                    if(smallPkt <= availableSmallPackages ) {
                        result = smallPkt + availableLargePackages;
                    }                   
                }
                else if( units == items) {
                    result = availableLargePackages;
                }
                else if(units > items) {
                    biggerUnits = items/5;
                    smallPkt = items%5;
                    result = biggerUnits + smallPkt;
                }               
            }
            else {
                if(items == availableLargePackages * 5) {
                    return availableLargePackages;
                }
                else if(items > availableLargePackages * 5) {
                    return result;
                }
                else if(items < availableLargePackages * 5) {
                   return result;   
                }
            }           
          } 
        else {
            if( items == availableSmallPackages) {
                return availableSmallPackages;
            }
            else if(items < availableSmallPackages){
              return (items - availableSmallPackages);
            }
            else
                return result;
        }                               
        return result;
    }
    
    public int minimalNumberOfPackagesCh3(int items, int availableLargePackages, int availableSmallPackages) {

        int units = 0;
        int biggerUnits = 0;
        int smallPkt = 0;
        int result = -1;
        if (availableLargePackages != 0) {
            units = 5 * availableLargePackages;
            if (availableSmallPackages != 0) {
                if (units < items) {
                    smallPkt = items - units;
                    if (smallPkt <= availableSmallPackages) {
                        result = smallPkt + availableLargePackages;
                    }
                } else if (units == items) {
                    result = availableLargePackages;
                } else if (units > items) {
                    biggerUnits = items / 5;
                    smallPkt = items % 5;
                    result = biggerUnits + smallPkt;
                }
            } else {
                if (items == availableLargePackages * 5) {
                    return availableLargePackages;
                } else {
                    return result;
                }
            }
        } else {
            if (items == availableSmallPackages) {
                return availableSmallPackages;
            } else if (items < availableSmallPackages) {
                return (items - availableSmallPackages);
            } else
                return result;
        }
        return result;
    }

    public int minimalNumberOfPackagesCh4(int items, int availableLargePackages, int availableSmallPackages) {
        // 22, 6, 1, -1
        int units = 0;
        int biggerUnits = 0;
        int smallPkt = 0;
        int result = -1;
        if (availableLargePackages != 0) {
            units = 5 * availableLargePackages;
            if (availableSmallPackages != 0) {
                if (units < items) {
                    smallPkt = items - units;
                    if (smallPkt <= availableSmallPackages) {
                        result = smallPkt + availableLargePackages;
                    }
                } else if (units == items) {
                    result = availableLargePackages;
                } else if (units > items) {
                    biggerUnits = items / 5;
                    smallPkt = items % 5;
                    if (smallPkt <= availableSmallPackages) {
                        result = biggerUnits + smallPkt;
                    }

                }
            } else {
                if (items == availableLargePackages * 5) {
                    result = availableLargePackages;
                }
            }
        } else {
            if (items == availableSmallPackages) {
                result = availableSmallPackages;
            } else if (items < availableSmallPackages) {
                result = (items - availableSmallPackages);
            }
        }
        return result;
    }
}
