# 前言
之前一直没有写简介，比较简陋，现在简单介绍一些这个项目，这个计步器的主要实现是基于手机的加速度感应器实现的，不是根据GPS实现的。

# 记步
经过两个月的努力，终于把计步器APP初步完成，已经可以正常使用，话不多说，首先上几张APP正常运行的图片：
首先这是计步器(Pedometer)主页面——记步页面

![这里写图片描述](http://img.blog.csdn.net/20170430225441009?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

点击中间部分，可以更换显示的数据，分别有步数，消耗的卡路里以及当前的天气情况。下面是分别几个界面：

![这里写图片描述](http://img.blog.csdn.net/20170430225454364?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430225504411?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

还可以分享到相应设社交平台上

![这里写图片描述](http://img.blog.csdn.net/20170430225516020?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


大家可以清晰的看到，图中的圆圈是一个ProgressBar（进度条）,随着圈内的数值的增加，ProgressBar也在动态的增加。
首先进入页面的是显示步数，点击圆圈会显示消耗的卡路里，再次点击圆圈会显示当天的天气情况（现在仅仅是北京的天气），最后点击左上方的图标，是一个分享的按钮。
这个重新定义的ProgressBar当初画了好几天，才做成的。

# 历史

点击左侧的历史页面，会进入近期步数的记录页面，这里对数据进行本地化存储。

![这里写图片描述](http://img.blog.csdn.net/20170430225608848?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430225618364?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

大家可以看到这个是历史页面，也就是记录每一天的所走的步数，点击左上角的日历的图标，会出现一个Dialog来显示日期，选择日期。
这个页面的步数，使用了动画，数字动态的从0到当天的步数，在2秒内完成。

# 分析

正如大家所见，这个页面是一个条形统计的页面，记录最近一周，每天的步数，更加直观的看到7天内的步数。此页面底部的星期，是可以动态变化的，开头总是当天的星期。这个柱状图可以动态的，当打开这个页面后，柱状图从底部上升到它所对应的数值（在1秒内完成），而且点击各个柱状图都能显示这个柱状图所代表的步数。

![这里写图片描述](http://img.blog.csdn.net/20170430225815162?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430225827451?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



# 个人信息设置

这个页面主要是设置一些个人信息，例如头像，姓名，年龄等基本情况，也可以对计步器的灵敏度进行调节。

![这里写图片描述](http://img.blog.csdn.net/20170430225911226?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

如图所示，大家应该都很清楚了，但是还是容小弟介绍一下，这里我把图片进行处理，设置成圆形的（更加美观）。点击头像可以选择：拍照或者相册。

# pk

这是最后一个页面——PK页面，这个页面由于考虑到有服务器和数据的交互，也是特别难实现。由于这个项目是我一个人独自完成，其他两个人不太会，所以后台的服务器我就使用了第三方的API（Bmob）。
第一张图是多人的PK，依次排名显示，单击每个人头像就会显示此人的详细信息（可以删除此人，图2所示）。向左滑动页面，进入分组PK页面，这里所有的人都分好组，然后点击小组下的人，可以进入图4的页面，此页面主要就是对这个成员进行分组调整。回到PK页面，点击右上角的小人标志，就可以连接服务器，添加好友。


![这里写图片描述](http://img.blog.csdn.net/20170430230054008?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430230104564?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430230115767?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430230125892?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170430230137430?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTI5Njc3NzUxMw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 总结

这个项目，可以作为入门的新手练手。因为很久之前的写的，本地的操作都可以正常运行，但是网络会出现一些问题，使用的第三方的后台。
