This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `jmh-reports/reports/jmh/`.

The main benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.  

The extra benchmark relate to options that affect performance.  
Currently, benchmarks for deserialization of `Collection` (mainly to check the impact of `strictNullChecks`)
and benchmarks for `value class` have been implemented.  
Also, these benchmarks are not run by default,
and benchmarks related to deserialization of `value class` are only run when using `Kogera`.

A simple configuration is provided in arguments.  
By rewriting the `isSingleShot` property, you can benchmark the performance on the first run.  
By rewriting the `isOnlyMain` property, you can exec all benchmarks.  
By rewriting the `mapper` property, you can benchmark using the `ObjectMapper` configuration defined in [org.wrongwrong.Mapper](./src/jmh/kotlin/org/wrongwrong/Mapper.kt).

Available `mapper` types are as follows

- `Kogera`(default): Not customized `KogeraModule`
- `Original`: Not customized `KotlinModule`
- `KogeraStrictNullCheck`: `KogeraModule` with enabled `strictNullChecks`
- `OriginalStrictNullCheck`: `OriginalModule` with enabled `strictNullChecks`

The following is an example of running all four benchmarks described in the `README` in succession.

```shell
./gradlew jmh -Pmapper=Original -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -Pmapper=Original -PisSingleShot=false -PisOnlyMain=false;
./gradlew jmh -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisSingleShot=false -PisOnlyMain=false
```

If you want to specify more detailed options, edit `build.gradle.kts`.

# Results
The currently published scores compare `jackson-module-kogera 2.15.2-beta1` with `jackson-module-kotlin 2.15.2`.  
Please note that these scores are measured by `GitHub Actions` and are not always the same.  
Also, the cache may stop updating the images for up to 5 minutes,
and the `README` may not have been updated after the benchmark was run,
so the description and images may not always match.

The letters `A` and `E` in the benchmark names are values to adjust the sort order of the results
and represent the number of properties (1 = `A`, 5 = `E`, ...).

The deserialization benchmarks show four patterns of values per number of properties,
depending on the creator invoked and the use of default arguments.  
The `Ctor` and `Func` in the title indicate that the deserialization was done by a constructor or factory function.  
The `present` and `missing` indicate that all arguments were read from the input or all were not read (= default arguments were used).

Raw data is stored in [ci-reports](./ci-reports).  
The spreadsheet used to generate the graphs can be found [here](https://drive.google.com/drive/folders/1mNa-bPybvhNTkGX7qkOfii_iMOI_gODG?usp=drive_link).

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

### Deserialize
`kogera` is particularly good at deserialization.

![](https://docs.google.com/spreadsheets/d/e/2PACX-1vTZB9ByuRV9XS_eug0vM_IEx_Em_ObiuZMoClXAt7zVZQZ9EnhKCXmbTsRQpoLiBbje6H_R9Hf7v0RI/pubchart?oid=754117157&format=image)

### Serialize
As for serialization performance, `Kogera` is slightly inferior.   
This may be due to the fact that `Original`'s getters are handled by the almost plain `Jackson`,
while `Kogera` sets an implicit name.

![](https://docs.google.com/spreadsheets/d/e/2PACX-1vTZB9ByuRV9XS_eug0vM_IEx_Em_ObiuZMoClXAt7zVZQZ9EnhKCXmbTsRQpoLiBbje6H_R9Hf7v0RI/pubchart?oid=1424094114&format=image)

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

### Deserialize
Although not as different as the throughput mode, the trend is toward `Kogera` being better.  

![](https://docs.google.com/spreadsheets/d/e/2PACX-1vSkI9k_uQtaxfmvCTuXpU7u5KzTteMV63O8Uz4dod_LeuSRX-z2ZxR4J7broxtJVGa0zcnxbbPgLZeC/pubchart?oid=754117157&format=image)

### Serialize
As for serialization, the difference is larger than in throughput mode, and I am considering improvements.

![](https://docs.google.com/spreadsheets/d/e/2PACX-1vSkI9k_uQtaxfmvCTuXpU7u5KzTteMV63O8Uz4dod_LeuSRX-z2ZxR4J7broxtJVGa0zcnxbbPgLZeC/pubchart?oid=1424094114&format=image)

## Comparison of normal class and `value class`
This section compares the serialization and deserialization performance using `Kogera`
for the `value class` and the `data class` (`wrapper`).

Overall, the `value class` scores are significantly inferior.  
This is especially true for serialization.  

The reason for this is that the `value class` requires more reflection processing.

### Throughput mode
#### Deserialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQr4_nhi0wY05LfKLJobcRMUWgn-nACTuDmMsJSNhstbiA0ZDaDQqQfmAiTzjGrn6HhcihdywsDC2XY/pubchart?oid=1135896566&format=image)

#### Serialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQr4_nhi0wY05LfKLJobcRMUWgn-nACTuDmMsJSNhstbiA0ZDaDQqQfmAiTzjGrn6HhcihdywsDC2XY/pubchart?oid=746620310&format=image)

### SingleShot mode
Although the difference is smaller in SingleShot mode,
the tendency for inferior scores in the `value class` is still present.

#### Deserialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQr4_nhi0wY05LfKLJobcRMUWgn-nACTuDmMsJSNhstbiA0ZDaDQqQfmAiTzjGrn6HhcihdywsDC2XY/pubchart?oid=1507400004&format=image)

#### Serialize
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQr4_nhi0wY05LfKLJobcRMUWgn-nACTuDmMsJSNhstbiA0ZDaDQqQfmAiTzjGrn6HhcihdywsDC2XY/pubchart?oid=2008357899&format=image)

## Comparison of strictNullChecks
If the `StrictNullChecks` option is enabled,
the deserialization score associated with the `Collection` is reduced because of element checking.  
Also, `Original` will theoretically lower the score for all deserialization operations because of the `null` checks required for all arguments on every call.  
On the other hand, `Kogera` has its own improvements to reduce these score reductions.

Here are the benchmark scores defined in `extra.deser.Collections` and `main.deser`.

### Throughput mode
For the `Collection`, there is almost no difference for `Kogera`, whereas for `Original` there is a relatively large difference.  
There is not much difference except for the `Collection`.

#### original
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vSgFV1Qiv8WWxJ83wUVJXVU9xagYSC7YVcCZAu3S51-VSBDT33qdJkqq3-Gqdl5vQHJsxkUPvSjihhv/pubchart?oid=351902777&format=image)

#### kogera
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vRtSIkLr1stFR2taQczT53qdiqlGzg3_j9xFyi9woBVTKSNEcen6h9-9jKvCLKTJUWSoLWFNKnoJpr6/pubchart?oid=351902777&format=image)

### SingleShot mode
In `Kogera`, there is more score reduction when `StrictNullChecks` is enabled than in `Original`.

#### original
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vQixP8gQJ1uiQTCiXWDfYi4SmKBeSlJfBZx2uZ34PJIFBR454T_ubMrz2_lma0VVxKGOwkWnZRgqEes/pubchart?oid=351902777&format=image)

#### kogera
![](https://docs.google.com/spreadsheets/d/e/2PACX-1vTPItNnF2urmh3xbGpsbD_1CvUzKO6cTKSQYHQrZTTUb5e0s_sYvELJXjTp3yqDBsBbKx6bSFjypa_M/pubchart?oid=351902777&format=image)
