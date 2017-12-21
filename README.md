# Movie Recommendation System

**DataSet** : Netflix

**Algorithm** : Item Collaborative Filtering

* A form of collaborative filtering based on the similarity between items calculated using people's ratings of those items

* Why using Item CF rather than user CF
    * The number of users weighs more than number of products
    * Item will not change frequently, lowering calculation
    * Using user’s historical data, more convincing
    
* How to implement Item CF
    * Build co-occurrence matrix
    * Build rating matrix
    * Matrix computation to get recommending result
 
* How to define similarity between two movies
    * If one user rated two movies, these two are related
    
* co-occurrence matrix:
    * A co-occurrence matrix is a matrix that is defined over an image to be the distribution of co-occurring pixel values (grayscale values, or colors) at agiven offset.


**Five MapReducer used**

![img1](https://github.com/chen-star/Movie-Recommendation-System/raw/master/img/img1.JPG)
![img2](https://github.com/chen-star/Movie-Recommendation-System/raw/master/img/img2.JPG)
![img3](https://github.com/chen-star/Movie-Recommendation-System/raw/master/img/img3.JPG)
![img4](https://github.com/chen-star/Movie-Recommendation-System/raw/master/img/img4.JPG)








