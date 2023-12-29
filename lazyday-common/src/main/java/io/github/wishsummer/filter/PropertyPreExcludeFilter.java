package io.github.wishsummer.filter;

import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import lombok.NoArgsConstructor;

/**
 * Description:排除json敏感字段
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: PropertyPreExcludeFilter.java, 2023/12/26 13:49 $
 */
@NoArgsConstructor
public class PropertyPreExcludeFilter extends SimplePropertyPreFilter {

    public PropertyPreExcludeFilter addExcludes(String[] excludes) {
        for (int i = 0; i < excludes.length; i++) {
            this.getExcludes().add(excludes[i]);
        }
        return this;
    }
}
