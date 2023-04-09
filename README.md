This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `jmh-reports/reports/jmh/`.

The main benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.  

The extra benchmark relate to options that affect performance.  
Currently, only benchmarks for `strictNullChecks` are implemented.  
Also, these benchmarks are not run by default.

A simple configuration is provided in arguments.  
By rewriting the `isKogera` property, you can run benchmarks on `jackson-module-kotlin` (but exclude benchmarks on non-supported content).  
By rewriting the `isSingleShot` property, you can benchmark the performance on the first run.  
By rewriting the `isOnlyMain` property, you can exec all benchmarks.

The following is an example of running all four benchmarks described in the `README` in succession.

```shell
./gradlew jmh -PisKogera=false -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisKogera=false -PisSingleShot=false -PisOnlyMain=false;
./gradlew jmh -PisKogera=true -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisKogera=true -PisSingleShot=false -PisOnlyMain=false
```

If you want to specify more detailed options, edit `build.gradle.kts`.

# Results
For ease of comparison, descriptions of kogera-specific implementations are separated.

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

`kogera` is particularly good at deserialization, being just under three times faster in some use cases.  
Also, the performance degradation with `kogera` is relatively small when `strictNullChecks` is enabled.

There is no significant difference in serialization performance.

#### original(2.14.2)
```
Benchmark                                           Mode  Cnt        Score        Error  Units
o.w.extra.deser.StrictNullChecks.array             thrpt    4   797588.955 ±  80398.807  ops/s
o.w.extra.deser.StrictNullChecks.arrayStrict       thrpt    4   664530.049 ±  32462.667  ops/s
o.w.extra.deser.StrictNullChecks.list              thrpt    4   836248.909 ± 123957.572  ops/s
o.w.extra.deser.StrictNullChecks.listStrict        thrpt    4   645393.860 ± 231053.704  ops/s
o.w.extra.deser.StrictNullChecks.map               thrpt    4   731284.168 ±  39967.350  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict         thrpt    4   573184.029 ± 164115.997  ops/s
o.w.main.deser.A_1Props_Constructor.call           thrpt    4  1025963.172 ±  82391.684  ops/s
o.w.main.deser.A_1Props_Constructor.call_default   thrpt    4   467234.901 ±  39330.088  ops/s
o.w.main.deser.A_1Props_Function.call              thrpt    4   639565.186 ±  21893.286  ops/s
o.w.main.deser.A_1Props_Function.call_default      thrpt    4   385398.847 ± 310861.198  ops/s
o.w.main.deser.E_5Props_Constructor.call           thrpt    4   413035.711 ±  27994.190  ops/s
o.w.main.deser.E_5Props_Constructor.call_default   thrpt    4   199301.796 ±  24764.256  ops/s
o.w.main.deser.E_5Props_Function.call              thrpt    4   266335.327 ±  13796.692  ops/s
o.w.main.deser.E_5Props_Function.call_default      thrpt    4   175854.601 ±  13930.316  ops/s
o.w.main.deser.T_20Props_Constructor.call          thrpt    4   121820.390 ±   9408.291  ops/s
o.w.main.deser.T_20Props_Constructor.call_default  thrpt    4    58713.080 ±  29745.814  ops/s
o.w.main.deser.T_20Props_Function.call             thrpt    4    82129.560 ±  18490.949  ops/s
o.w.main.deser.T_20Props_Function.call_default     thrpt    4    59745.649 ±  17749.454  ops/s
o.w.main.ser.A_1Props.call                         thrpt    4  2393774.664 ± 260296.107  ops/s
o.w.main.ser.E_5Props.call                         thrpt    4  1462948.533 ± 192544.015  ops/s
o.w.main.ser.T_20Props.call                        thrpt    4   488443.459 ± 352681.348  ops/s
```

#### kogera(2.14.2-alpha4)
```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.deser.StrictNullChecks.array                          thrpt    4   971098.525 ±   33876.209  ops/s
o.w.extra.deser.StrictNullChecks.arrayStrict                    thrpt    4   940518.329 ±   46724.201  ops/s
o.w.extra.deser.StrictNullChecks.list                           thrpt    4   955293.158 ±  192812.208  ops/s
o.w.extra.deser.StrictNullChecks.listStrict                     thrpt    4  1007509.262 ±   45758.360  ops/s
o.w.extra.deser.StrictNullChecks.map                            thrpt    4   878082.583 ±   44728.329  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict                      thrpt    4   870435.728 ±   87736.817  ops/s
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1212909.001 ±  218147.413  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   952608.154 ±  243288.680  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4  1215083.790 ±  146167.230  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   750642.394 ±   66571.453  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   641811.853 ±   13562.336  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   481967.428 ±   82893.545  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   627919.486 ±  191582.778  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   447195.318 ±   39711.588  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   247861.795 ±   37897.688  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   166471.382 ±   11832.886  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   236874.315 ±   13353.918  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   153711.846 ±    7529.606  ops/s
o.w.main.ser.A_1Props.call                                      thrpt    4  2490405.115 ± 1540867.842  ops/s
o.w.main.ser.E_5Props.call                                      thrpt    4  1451720.430 ±  155561.785  ops/s
o.w.main.ser.T_20Props.call                                     thrpt    4   515172.268 ±   28482.979  ops/s
```

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

There is not much difference in deserialization, but `kogera` seems to have areas that can be improved in terms of serialization.

#### original(2.14.2)
```
Benchmark                                          Mode  Cnt   Score   Error  Units
o.w.extra.deser.StrictNullChecks.array               ss    5  28.486 ± 3.526  ms/op
o.w.extra.deser.StrictNullChecks.arrayStrict         ss    5  27.367 ± 1.682  ms/op
o.w.extra.deser.StrictNullChecks.list                ss    5  24.014 ± 1.478  ms/op
o.w.extra.deser.StrictNullChecks.listStrict          ss    5  22.787 ± 3.361  ms/op
o.w.extra.deser.StrictNullChecks.map                 ss    5  25.637 ± 1.178  ms/op
o.w.extra.deser.StrictNullChecks.mapStrict           ss    5  24.526 ± 1.108  ms/op
o.w.main.deser.A_1Props_Constructor.call             ss    5  20.108 ± 1.170  ms/op
o.w.main.deser.A_1Props_Constructor.call_default     ss    5  21.298 ± 1.220  ms/op
o.w.main.deser.A_1Props_Function.call                ss    5  22.101 ± 2.862  ms/op
o.w.main.deser.A_1Props_Function.call_default        ss    5  23.323 ± 1.920  ms/op
o.w.main.deser.E_5Props_Constructor.call             ss    5  20.506 ± 0.904  ms/op
o.w.main.deser.E_5Props_Constructor.call_default     ss    5  22.768 ± 4.259  ms/op
o.w.main.deser.E_5Props_Function.call                ss    5  23.333 ± 1.071  ms/op
o.w.main.deser.E_5Props_Function.call_default        ss    5  24.807 ± 2.349  ms/op
o.w.main.deser.T_20Props_Constructor.call            ss    5  24.011 ± 2.538  ms/op
o.w.main.deser.T_20Props_Constructor.call_default    ss    5  25.850 ± 2.553  ms/op
o.w.main.deser.T_20Props_Function.call               ss    5  27.583 ± 1.509  ms/op
o.w.main.deser.T_20Props_Function.call_default       ss    5  29.311 ± 1.715  ms/op
o.w.main.ser.A_1Props.call                           ss    5  55.455 ± 4.316  ms/op
o.w.main.ser.E_5Props.call                           ss    5  57.762 ± 2.420  ms/op
o.w.main.ser.T_20Props.call                          ss    5  68.171 ± 4.034  ms/op
```

#### kogera(2.14.2-alpha4)
```
Benchmark                                                       Mode  Cnt    Score   Error  Units
o.w.extra.deser.StrictNullChecks.array                            ss    5   27.320 ± 7.458  ms/op
o.w.extra.deser.StrictNullChecks.arrayStrict                      ss    5   26.523 ± 7.922  ms/op
o.w.extra.deser.StrictNullChecks.list                             ss    5   26.987 ± 5.830  ms/op
o.w.extra.deser.StrictNullChecks.listStrict                       ss    5   25.441 ± 4.611  ms/op
o.w.extra.deser.StrictNullChecks.map                              ss    5   29.492 ± 7.648  ms/op
o.w.extra.deser.StrictNullChecks.mapStrict                        ss    5   27.622 ± 9.015  ms/op
o.w.main.deser.A_1Props_Constructor.call                          ss    5   20.932 ± 2.287  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5   22.320 ± 1.936  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5   21.248 ± 0.870  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5   23.284 ± 3.161  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5   22.900 ± 2.183  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5   24.535 ± 1.843  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5   23.916 ± 1.057  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5   25.650 ± 2.925  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5   27.073 ± 2.290  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5   28.081 ± 2.352  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5   28.103 ± 2.714  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5   29.827 ± 2.978  ms/op
o.w.main.ser.A_1Props.call                                        ss    5   70.627 ± 2.638  ms/op
o.w.main.ser.E_5Props.call                                        ss    5   72.313 ± 1.877  ms/op
o.w.main.ser.T_20Props.call                                       ss    5   80.913 ± 5.972  ms/op
```

## Comparison of normal class and `value class`
This section presents benchmarks for the use of `value class` with `kogera`.  
Note that the `value class` side is at a disadvantage since this is a comparison of a pure `primitive` type and a `value class`.

A comparison with the `data class` that wraps the value will be created in the future.

### Throughput mode
The use of `value class` has resulted in an overall decrease in speed.  
In particular, serialization scores are significantly lower.

#### normal class
```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1212909.001 ±  218147.413  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   952608.154 ±  243288.680  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4  1215083.790 ±  146167.230  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   750642.394 ±   66571.453  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   641811.853 ±   13562.336  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   481967.428 ±   82893.545  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   627919.486 ±  191582.778  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   447195.318 ±   39711.588  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   247861.795 ±   37897.688  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   166471.382 ±   11832.886  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   236874.315 ±   13353.918  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   153711.846 ±    7529.606  ops/s
o.w.main.ser.A_1Props.call                                      thrpt    4  2490405.115 ± 1540867.842  ops/s
o.w.main.ser.E_5Props.call                                      thrpt    4  1451720.430 ±  155561.785  ops/s
o.w.main.ser.T_20Props.call                                     thrpt    4   515172.268 ±   28482.979  ops/s
```

#### value class
```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.value_class.deser.A_1Props_Constructor.call           thrpt    4   999924.428 ±  138944.862  ops/s
o.w.extra.value_class.deser.A_1Props_Constructor.call_default   thrpt    4   943279.144 ±  128326.108  ops/s
o.w.extra.value_class.deser.A_1Props_Function.call              thrpt    4  1054906.554 ±   86793.377  ops/s
o.w.extra.value_class.deser.A_1Props_Function.call_default      thrpt    4   756254.524 ±   13604.402  ops/s
o.w.extra.value_class.deser.E_5Props_Constructor.call           thrpt    4   464590.083 ±   36621.049  ops/s
o.w.extra.value_class.deser.E_5Props_Constructor.call_default   thrpt    4   499799.563 ±   18279.885  ops/s
o.w.extra.value_class.deser.E_5Props_Function.call              thrpt    4   457057.888 ±   14090.650  ops/s
o.w.extra.value_class.deser.E_5Props_Function.call_default      thrpt    4   427930.579 ±   18395.565  ops/s
o.w.extra.value_class.deser.T_20Props_Constructor.call          thrpt    4   146024.262 ±    2926.256  ops/s
o.w.extra.value_class.deser.T_20Props_Constructor.call_default  thrpt    4   165905.529 ±    9091.975  ops/s
o.w.extra.value_class.deser.T_20Props_Function.call             thrpt    4   134063.331 ±   49451.321  ops/s
o.w.extra.value_class.deser.T_20Props_Function.call_default     thrpt    4   156236.819 ±   26232.356  ops/s
o.w.extra.value_class.ser.A_1Props.call                         thrpt    4  1431291.309 ±  803563.341  ops/s
o.w.extra.value_class.ser.E_5Props.call                         thrpt    4   560283.698 ±   58427.418  ops/s
o.w.extra.value_class.ser.T_20Props.call                        thrpt    4   172373.156 ±    8435.729  ops/s
```

### SingleShot mode
The scores here also show the same trend as in Throughput mode.

#### normal class
```
o.w.main.deser.A_1Props_Constructor.call                          ss    5   20.932 ± 2.287  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5   22.320 ± 1.936  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5   21.248 ± 0.870  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5   23.284 ± 3.161  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5   22.900 ± 2.183  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5   24.535 ± 1.843  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5   23.916 ± 1.057  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5   25.650 ± 2.925  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5   27.073 ± 2.290  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5   28.081 ± 2.352  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5   28.103 ± 2.714  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5   29.827 ± 2.978  ms/op
o.w.main.ser.A_1Props.call                                        ss    5   70.627 ± 2.638  ms/op
o.w.main.ser.E_5Props.call                                        ss    5   72.313 ± 1.877  ms/op
o.w.main.ser.T_20Props.call                                       ss    5   80.913 ± 5.972  ms/op
```

#### value class
```
o.w.extra.value_class.deser.A_1Props_Constructor.call             ss    5   21.409 ± 0.569  ms/op
o.w.extra.value_class.deser.A_1Props_Constructor.call_default     ss    5   22.157 ± 1.915  ms/op
o.w.extra.value_class.deser.A_1Props_Function.call                ss    5   22.256 ± 0.774  ms/op
o.w.extra.value_class.deser.A_1Props_Function.call_default        ss    5   22.259 ± 1.323  ms/op
o.w.extra.value_class.deser.E_5Props_Constructor.call             ss    5   23.976 ± 0.996  ms/op
o.w.extra.value_class.deser.E_5Props_Constructor.call_default     ss    5   23.713 ± 2.203  ms/op
o.w.extra.value_class.deser.E_5Props_Function.call                ss    5   25.171 ± 0.527  ms/op
o.w.extra.value_class.deser.E_5Props_Function.call_default        ss    5   25.528 ± 3.618  ms/op
o.w.extra.value_class.deser.T_20Props_Constructor.call            ss    5   29.248 ± 3.550  ms/op
o.w.extra.value_class.deser.T_20Props_Constructor.call_default    ss    5   28.601 ± 2.493  ms/op
o.w.extra.value_class.deser.T_20Props_Function.call               ss    5   30.979 ± 3.471  ms/op
o.w.extra.value_class.deser.T_20Props_Function.call_default       ss    5   31.022 ± 4.597  ms/op
o.w.extra.value_class.ser.A_1Props.call                           ss    5  179.511 ± 3.305  ms/op
o.w.extra.value_class.ser.E_5Props.call                           ss    5  181.417 ± 9.972  ms/op
o.w.extra.value_class.ser.T_20Props.call                          ss    5  190.209 ± 4.594  ms/op
```