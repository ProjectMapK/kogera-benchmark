This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `${project.buildDir}/reports/jmh/`.

The main benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.  

The extra benchmark relate to options that affect performance.  
Currently, only benchmarks for `strictNullChecks` are implemented.  
Also, these benchmarks are not run by default.

A simple configuration is provided in `build.gradle.kts`.  
By rewriting the `isKogera` property, you can compare with the original.  
By rewriting the `isSingleShot` property, you can compare the performance of the first run.  
By rewriting the `isOnlyMain` property, you can exec all benchmarks.

# Results
The results shown here are for the following commit.  
https://github.com/ProjectMapK/jackson-module-kogera/tree/01cad94664f1504ae4cdd2da144b3d56b4e3efce

The version of `jackson-module-kotlin` is the same as the version of this commit.

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

`kogera` is particularly good at deserialization, being just under three times faster in some use cases.  
Also, the performance degradation with `kogera` is relatively small when `strictNullChecks` is enabled.

There is no significant difference in serialization performance.

### original
```
Benchmark                                           Mode  Cnt        Score         Error  Units
o.w.extra.deser.StrictNullChecks.array             thrpt    4   788631.252 ±   56981.231  ops/s
o.w.extra.deser.StrictNullChecks.arrayStrict       thrpt    4   665656.537 ±   33375.375  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4   844107.697 ±   53169.624  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   687755.266 ±   60128.550  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   752715.970 ±   29195.905  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   585182.025 ±   13587.045  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4   989044.290 ±  258352.107  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   478764.605 ±   69549.425  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4   607382.191 ±  128967.597  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   431132.447 ±   30361.474  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   408213.075 ±   33382.180  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   200298.099 ±    4725.503  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   259168.045 ±   37760.777  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   178720.692 ±   12242.207  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   124655.481 ±   18953.708  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4    60219.846 ±   16538.018  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4    82851.478 ±    6447.900  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4    58208.582 ±   20761.643  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2549237.744 ± 1234052.153  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1459062.930 ±   95093.781  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   533113.542 ±   36285.744  ops/s
```

### kogera(2.14.1-alpha2)
```
Benchmark                                           Mode  Cnt        Score        Error  Units
o.w.extra.deser.StrictNullChecks.array             thrpt    4   956090.117 ±  67110.478  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4  1002999.405 ±  36347.056  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   984688.722 ±  68700.085  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   877900.352 ± 111825.689  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   845724.491 ± 180757.319  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4  1239860.209 ±  42837.235  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   917702.904 ±  77935.197  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4  1239210.227 ± 208272.925  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   748847.934 ±  40398.201  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   660545.774 ± 131176.843  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   497135.835 ±  54141.224  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   680163.159 ±  76863.440  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   434732.197 ±  64742.019  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   243756.717 ±   5839.189  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4   164210.082 ±  29258.051  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4   245989.443 ±  64479.355  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4   152563.530 ±  11113.840  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2400573.824 ± 675494.208  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1442174.652 ± 128287.702  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   529424.873 ±  22888.027  ops/s

```

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

There is not much difference in deserialization, but `kogera` seems to have areas that can be improved in terms of serialization.

### original
```
Benchmark                                     Mode  Cnt   Score   Error  Units
o.w.deser.A_1Props_Constructor.call             ss    5  20.021 ± 1.290  ms/op
o.w.deser.A_1Props_Constructor.call_default     ss    5  21.482 ± 1.143  ms/op
o.w.deser.A_1Props_Function.call                ss    5  21.852 ± 2.234  ms/op
o.w.deser.A_1Props_Function.call_default        ss    5  22.854 ± 1.051  ms/op
o.w.deser.E_5Props_Constructor.call             ss    5  20.855 ± 1.978  ms/op
o.w.deser.E_5Props_Constructor.call_default     ss    5  22.347 ± 1.030  ms/op
o.w.deser.E_5Props_Function.call                ss    5  23.280 ± 0.715  ms/op
o.w.deser.E_5Props_Function.call_default        ss    5  24.636 ± 1.458  ms/op
o.w.deser.T_20Props_Constructor.call            ss    5  23.945 ± 1.502  ms/op
o.w.deser.T_20Props_Constructor.call_default    ss    5  25.913 ± 1.791  ms/op
o.w.deser.T_20Props_Function.call               ss    5  28.484 ± 2.875  ms/op
o.w.deser.T_20Props_Function.call_default       ss    5  29.572 ± 1.218  ms/op
o.w.ser.A_1Props.call                           ss    5  55.035 ± 3.071  ms/op
o.w.ser.E_5Props.call                           ss    5  59.042 ± 3.804  ms/op
o.w.ser.T_20Props.call                          ss    5  67.898 ± 2.479  ms/op
```

### kogera(2.14.2)
```
Benchmark                                       Mode  Cnt   Score   Error  Units
o.w.m.deser.A_1Props_Constructor.call             ss    5  21.010 ± 1.361  ms/op
o.w.m.deser.A_1Props_Constructor.call_default     ss    5  22.444 ± 0.722  ms/op
o.w.m.deser.A_1Props_Function.call                ss    5  21.739 ± 1.460  ms/op
o.w.m.deser.A_1Props_Function.call_default        ss    5  23.136 ± 2.382  ms/op
o.w.m.deser.E_5Props_Constructor.call             ss    5  23.523 ± 0.472  ms/op
o.w.m.deser.E_5Props_Constructor.call_default     ss    5  24.957 ± 1.894  ms/op
o.w.m.deser.E_5Props_Function.call                ss    5  24.426 ± 1.615  ms/op
o.w.m.deser.E_5Props_Function.call_default        ss    5  25.944 ± 2.602  ms/op
o.w.m.deser.T_20Props_Constructor.call            ss    5  27.611 ± 2.137  ms/op
o.w.m.deser.T_20Props_Constructor.call_default    ss    5  29.228 ± 1.855  ms/op
o.w.m.deser.T_20Props_Function.call               ss    5  29.693 ± 0.855  ms/op
o.w.m.deser.T_20Props_Function.call_default       ss    5  31.801 ± 2.640  ms/op
o.w.m.ser.A_1Props.call                           ss    5  72.660 ± 3.609  ms/op
o.w.m.ser.E_5Props.call                           ss    5  74.568 ± 8.153  ms/op
o.w.m.ser.T_20Props.call                          ss    5  83.585 ± 3.255  ms/op
```
