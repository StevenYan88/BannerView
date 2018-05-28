# BannerView

### 打造万能的BannerView

**为什么开源这个BannerView，因为在网上看到的绝大多数BannerView实现了右无限轮播图，甚至没有实现无限轮播图，说成是无限轮播图，实现了左右无限轮播图的，并没有做性能上的优化。**   

**性能优化：**  
1、离开窗口时handelr需要removeMessage；  
2、轮播图的View的复用；    
3、点击home键，轮播图还在内存中走，需要监听下Activity的生命周期；   


**效果图**

![image](https://upload-images.jianshu.io/upload_images/1472453-d0c6af365e6276ee.gif?imageMogr2/auto-orient/strip)

**布局去引用BannerView，设置下BannerView的属性**

    <com.steven.bannerview.banner.BannerView
        android:id="@+id/bannerView"
        android:layout_width="match_parent"
        android:layout_height="140dp"
        app:bottomColor="#453e3e3e"
        app:dotDistance="2dp"
        app:dotGravity="center"
        app:dotIndicatorFocus="#ff0000"
        app:dotIndicatorNormal="#ffffff"
        app:dotSize="6dp"/>
