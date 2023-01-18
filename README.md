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

### kogera(790db409416)
```
o.w.extra.deser.StrictNullChecks.array             thrpt    4   968999.635 ±  22231.973  ops/s
o.w.extra.deser.StrictNullChecks.arrayStrict       thrpt    4   921253.654 ±  25505.923  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4   989496.854 ±  32741.244  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   987504.414 ±  48033.178  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   886742.960 ±  25096.656  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   871995.858 ±  25538.408  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4  1221311.809 ±  94134.838  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   958209.206 ±  75025.877  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4  1199348.443 ±  52423.484  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   732714.781 ±  78290.138  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   661713.055 ± 125433.549  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   481910.932 ±  72936.356  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   656376.988 ±  96061.017  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   410847.289 ± 117227.726  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   237124.856 ±  10259.702  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4   144592.802 ±  25836.356  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4   241609.010 ±   7838.227  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4   157349.793 ±  12057.137  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2333857.282 ± 643068.529  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1393190.585 ± 105930.925  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   530833.839 ±  19199.730  ops/s
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

### kogera
```
Benchmark                                     Mode  Cnt   Score   Error  Units
o.w.deser.A_1Props_Constructor.call             ss    5  20.756 ± 1.813  ms/op
o.w.deser.A_1Props_Constructor.call_default     ss    5  21.861 ± 0.689  ms/op
o.w.deser.A_1Props_Function.call                ss    5  22.034 ± 3.631  ms/op
o.w.deser.A_1Props_Function.call_default        ss    5  23.171 ± 0.886  ms/op
o.w.deser.E_5Props_Constructor.call             ss    5  22.634 ± 0.604  ms/op
o.w.deser.E_5Props_Constructor.call_default     ss    5  24.435 ± 1.532  ms/op
o.w.deser.E_5Props_Function.call                ss    5  24.239 ± 1.774  ms/op
o.w.deser.E_5Props_Function.call_default        ss    5  25.658 ± 1.174  ms/op
o.w.deser.T_20Props_Constructor.call            ss    5  26.508 ± 0.943  ms/op
o.w.deser.T_20Props_Constructor.call_default    ss    5  27.831 ± 0.957  ms/op
o.w.deser.T_20Props_Function.call               ss    5  28.895 ± 2.484  ms/op
o.w.deser.T_20Props_Function.call_default       ss    5  30.278 ± 1.634  ms/op
o.w.ser.A_1Props.call                           ss    5  70.356 ± 5.123  ms/op
o.w.ser.E_5Props.call                           ss    5  72.016 ± 3.723  ms/op
o.w.ser.T_20Props.call                          ss    5  81.907 ± 5.221  ms/op
```
