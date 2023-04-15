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

#### kogera(2.14.2-alpha6)
```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.deser.StrictNullChecks.array                          thrpt    4   988939.215 ±    2260.870  ops/s
o.w.extra.deser.StrictNullChecks.arrayStrict                    thrpt    4   920564.698 ±  282571.967  ops/s
o.w.extra.deser.StrictNullChecks.list                           thrpt    4  1007240.390 ±   38379.302  ops/s
o.w.extra.deser.StrictNullChecks.listStrict                     thrpt    4   850810.680 ± 1069588.660  ops/s
o.w.extra.deser.StrictNullChecks.map                            thrpt    4   898147.204 ±   59550.546  ops/s
o.w.extra.deser.StrictNullChecks.mapStrict                      thrpt    4   882816.042 ±   44578.986  ops/s
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1229923.845 ±   90395.446  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   926313.673 ±  320233.416  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4  1221901.487 ±  197527.254  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   764671.483 ±   19771.651  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   663977.963 ±  192379.867  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   506505.526 ±   72362.665  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   688858.563 ±   25005.237  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   438832.667 ±   89453.794  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   239689.246 ±   22237.637  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   168186.473 ±   13695.658  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   233553.820 ±   66900.085  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   159912.264 ±    1980.403  ops/s
o.w.main.ser.A_1Props.call                                      thrpt    4  2613947.326 ± 1239765.940  ops/s
o.w.main.ser.E_5Props.call                                      thrpt    4  1297493.003 ±  248334.430  ops/s
o.w.main.ser.T_20Props.call                                     thrpt    4   484586.429 ±  295281.075  ops/s
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

#### kogera(2.14.2-alpha6)
```
Benchmark                                                       Mode  Cnt    Score   Error  Units
o.w.extra.deser.StrictNullChecks.array                            ss    5   25.468 ± 3.742  ms/op
o.w.extra.deser.StrictNullChecks.arrayStrict                      ss    5   26.020 ± 9.920  ms/op
o.w.extra.deser.StrictNullChecks.list                             ss    5   26.437 ± 4.681  ms/op
o.w.extra.deser.StrictNullChecks.listStrict                       ss    5   24.454 ± 6.432  ms/op
o.w.extra.deser.StrictNullChecks.map                              ss    5   27.499 ± 1.654  ms/op
o.w.extra.deser.StrictNullChecks.mapStrict                        ss    5   25.780 ± 6.848  ms/op
o.w.main.deser.A_1Props_Constructor.call                          ss    5   19.622 ± 0.699  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5   21.175 ± 1.318  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5   20.539 ± 1.125  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5   22.109 ± 0.935  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5   21.271 ± 0.743  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5   22.714 ± 0.555  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5   23.421 ± 1.789  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5   25.345 ± 2.369  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5   26.573 ± 1.923  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5   28.676 ± 3.144  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5   27.694 ± 2.113  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5   29.415 ± 3.154  ms/op
o.w.main.ser.A_1Props.call                                        ss    5   70.537 ± 2.737  ms/op
o.w.main.ser.E_5Props.call                                        ss    5   71.623 ± 1.802  ms/op
o.w.main.ser.T_20Props.call                                       ss    5   80.231 ± 1.304  ms/op
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
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1229923.845 ±   90395.446  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   926313.673 ±  320233.416  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4  1221901.487 ±  197527.254  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   764671.483 ±   19771.651  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   663977.963 ±  192379.867  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   506505.526 ±   72362.665  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   688858.563 ±   25005.237  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   438832.667 ±   89453.794  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   239689.246 ±   22237.637  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   168186.473 ±   13695.658  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   233553.820 ±   66900.085  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   159912.264 ±    1980.403  ops/s
o.w.main.ser.A_1Props.call                                      thrpt    4  2613947.326 ± 1239765.940  ops/s
o.w.main.ser.E_5Props.call                                      thrpt    4  1297493.003 ±  248334.430  ops/s
o.w.main.ser.T_20Props.call                                     thrpt    4   484586.429 ±  295281.075  ops/s
```

#### value class
```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.value_class.deser.A_1Props_Constructor.call           thrpt    4  1055157.528 ±   70689.644  ops/s
o.w.extra.value_class.deser.A_1Props_Constructor.call_default   thrpt    4   976121.639 ±   31665.421  ops/s
o.w.extra.value_class.deser.A_1Props_Function.call              thrpt    4  1032423.269 ±   11440.387  ops/s
o.w.extra.value_class.deser.A_1Props_Function.call_default      thrpt    4   765274.175 ±   16784.696  ops/s
o.w.extra.value_class.deser.E_5Props_Constructor.call           thrpt    4   445558.495 ±   76907.211  ops/s
o.w.extra.value_class.deser.E_5Props_Constructor.call_default   thrpt    4   503488.426 ±   45555.819  ops/s
o.w.extra.value_class.deser.E_5Props_Function.call              thrpt    4   463135.680 ±   11171.509  ops/s
o.w.extra.value_class.deser.E_5Props_Function.call_default      thrpt    4   443370.723 ±  100974.624  ops/s
o.w.extra.value_class.deser.T_20Props_Constructor.call          thrpt    4   144794.708 ±    7454.756  ops/s
o.w.extra.value_class.deser.T_20Props_Constructor.call_default  thrpt    4   167460.004 ±   18891.296  ops/s
o.w.extra.value_class.deser.T_20Props_Function.call             thrpt    4   149877.330 ±    2818.764  ops/s
o.w.extra.value_class.deser.T_20Props_Function.call_default     thrpt    4   159316.694 ±    6982.398  ops/s
o.w.extra.value_class.ser.A_1Props.call                         thrpt    4  1577216.744 ±   49465.931  ops/s
o.w.extra.value_class.ser.E_5Props.call                         thrpt    4   557590.485 ±   92354.171  ops/s
o.w.extra.value_class.ser.T_20Props.call                        thrpt    4   172207.004 ±   23721.896  ops/s
```

### SingleShot mode
The scores here also show the same trend as in Throughput mode.

#### normal class
```
Benchmark                                                       Mode  Cnt    Score   Error  Units
o.w.main.deser.A_1Props_Constructor.call                          ss    5   19.622 ± 0.699  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5   21.175 ± 1.318  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5   20.539 ± 1.125  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5   22.109 ± 0.935  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5   21.271 ± 0.743  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5   22.714 ± 0.555  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5   23.421 ± 1.789  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5   25.345 ± 2.369  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5   26.573 ± 1.923  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5   28.676 ± 3.144  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5   27.694 ± 2.113  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5   29.415 ± 3.154  ms/op
o.w.main.ser.A_1Props.call                                        ss    5   70.537 ± 2.737  ms/op
o.w.main.ser.E_5Props.call                                        ss    5   71.623 ± 1.802  ms/op
o.w.main.ser.T_20Props.call                                       ss    5   80.231 ± 1.304  ms/op
```

#### value class
```
Benchmark                                                       Mode  Cnt    Score   Error  Units
o.w.extra.value_class.deser.A_1Props_Constructor.call             ss    5   21.061 ± 0.911  ms/op
o.w.extra.value_class.deser.A_1Props_Constructor.call_default     ss    5   20.906 ± 0.725  ms/op
o.w.extra.value_class.deser.A_1Props_Function.call                ss    5   21.545 ± 0.925  ms/op
o.w.extra.value_class.deser.A_1Props_Function.call_default        ss    5   21.506 ± 1.960  ms/op
o.w.extra.value_class.deser.E_5Props_Constructor.call             ss    5   23.543 ± 2.288  ms/op
o.w.extra.value_class.deser.E_5Props_Constructor.call_default     ss    5   23.361 ± 1.658  ms/op
o.w.extra.value_class.deser.E_5Props_Function.call                ss    5   24.552 ± 1.211  ms/op
o.w.extra.value_class.deser.E_5Props_Function.call_default        ss    5   25.176 ± 2.414  ms/op
o.w.extra.value_class.deser.T_20Props_Constructor.call            ss    5   28.990 ± 2.078  ms/op
o.w.extra.value_class.deser.T_20Props_Constructor.call_default    ss    5   28.699 ± 2.185  ms/op
o.w.extra.value_class.deser.T_20Props_Function.call               ss    5   30.786 ± 4.326  ms/op
o.w.extra.value_class.deser.T_20Props_Function.call_default       ss    5   30.120 ± 1.592  ms/op
o.w.extra.value_class.ser.A_1Props.call                           ss    5  178.704 ± 7.453  ms/op
o.w.extra.value_class.ser.E_5Props.call                           ss    5  180.904 ± 5.945  ms/op
o.w.extra.value_class.ser.T_20Props.call                          ss    5  190.588 ± 8.277  ms/op
```