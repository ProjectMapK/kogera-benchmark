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

### kogera(2.14.1-alpha1-hotfix)
```
Benchmark                                           Mode  Cnt        Score         Error  Units
o.w.extra.deser.StrictNullChecks.array             thrpt    4   943445.307 �}   49209.038  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4  1004273.573 �}   47402.208  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   842937.081 �} 1115520.433  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   872452.500 �}   71162.570  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   858926.035 �}   44232.698  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4  1221527.041 �}  135683.634  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   958502.063 �}   87132.661  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4  1108791.222 �}  790931.384  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   752642.521 �}   23218.535  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   654951.859 �}  160062.900  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   487165.819 �}   88732.302  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   641341.629 �}   74857.602  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   425854.845 �}  170721.340  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   247436.325 �}   73713.301  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4   133562.488 �}   66551.415  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4   238201.084 �}    8658.807  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4   154396.792 �}   12588.314  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2399564.241 �}  224796.216  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1455413.558 �}  103668.468  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   532497.200 �}   31091.997  ops/s
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

### kogera(2.14.1-alpha1-hotfix)
```
Benchmark                                       Mode  Cnt   Score   Error  Units
o.w.m.deser.A_1Props_Constructor.call             ss    5  20.768 ± 1.178  ms/op
o.w.m.deser.A_1Props_Constructor.call_default     ss    5  22.429 ± 1.909  ms/op
o.w.m.deser.A_1Props_Function.call                ss    5  21.829 ± 1.843  ms/op
o.w.m.deser.A_1Props_Function.call_default        ss    5  23.162 ± 1.719  ms/op
o.w.m.deser.E_5Props_Constructor.call             ss    5  22.796 ± 2.256  ms/op
o.w.m.deser.E_5Props_Constructor.call_default     ss    5  24.870 ± 1.604  ms/op
o.w.m.deser.E_5Props_Function.call                ss    5  24.452 ± 2.280  ms/op
o.w.m.deser.E_5Props_Function.call_default        ss    5  26.127 ± 1.320  ms/op
o.w.m.deser.T_20Props_Constructor.call            ss    5  27.558 ± 1.774  ms/op
o.w.m.deser.T_20Props_Constructor.call_default    ss    5  29.070 ± 2.430  ms/op
o.w.m.deser.T_20Props_Function.call               ss    5  29.143 ± 2.029  ms/op
o.w.m.deser.T_20Props_Function.call_default       ss    5  31.117 ± 1.361  ms/op
o.w.m.ser.A_1Props.call                           ss    5  71.932 ± 4.146  ms/op
o.w.m.ser.E_5Props.call                           ss    5  75.345 ± 5.284  ms/op
o.w.m.ser.T_20Props.call                          ss    5  83.564 ± 2.718  ms/op
```
