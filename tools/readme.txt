1. dimenMultiply.py
python dimenMultiply.py inFile=./dimen.xml outFile=./dimen3.xml multi=2
参数说明：inFile： 源文件路径，outFile：输出文件路径，mult：系数（源文件中dimen值乘以系数，得到outFile中dimen值）
注意：
1.该脚本只会修改dimen标签的值，如果是integer标签则不修改;
2.定义的dimen值，如果是 dp和sp则会乘以系数，如果是px，则不变；
