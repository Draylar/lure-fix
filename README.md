# Lure Fix

A 1.16.2 Fabric mod that patches an issue where Lure does not work at levels 6+.

### Details

Fish wait time is calculated as `rand(100, 600) - lure * 100`. If the value is less than 0 (rand rolls 550, lure is 5), it is called again. 
In the case of lure 6, the resulting value will always be <=0, which means it will always attempt to reroll (and no fish will be caught).

This mod replaces the `lure * 100` call with a logistic curve that caps at around level 10 (Lure 10 = instant catch / 0 tick wait time).
You can see the graph of the curve [here](https://www.desmos.com/calculator/6j4xnb1sz4). The levels closely resemble vanilla for 1-3.

### License

Public Domain / CC0.
