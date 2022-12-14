/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.blog.quarkus.mapstruct;

import tech.ordinaryroad.blog.quarkus.dal.entity.BlogUser;
import tech.ordinaryroad.blog.quarkus.resource.vo.BlogIpRegionVO;
import tech.ordinaryroad.blog.quarkus.resource.vo.BlogUserVO;
import tech.ordinaryroad.blog.quarkus.service.BlogUserService;
import tech.ordinaryroad.blog.quarkus.util.BlogUtils;
import tech.ordinaryroad.blog.quarkus.util.IpRegion;

import javax.enterprise.inject.spi.CDI;
import java.util.Objects;

/**
 * @author mjz
 * @date 2022/8/30
 */
public interface BaseBlogMapStruct {

    default BlogUserVO string2UserVO(String createBy) {
        BlogUserService userService = CDI.current().select(BlogUserService.class).get();
        BlogUser user = userService.findById(createBy);
        return BlogUserMapStruct.INSTANCE.transfer(user);
    }

    default BlogIpRegionVO ip2Region(String ip) {
        IpRegion ipRegion = BlogUtils.INSTANCE.getIpRegion(ip);
        if (ipRegion == null) {
            return null;
        }
        String country = ipRegion.getCountry();
        if (Objects.equals(country, "0")) {
            country = ipRegion.getArea();
        }
        if (Objects.equals(country, "0")) {
            country = ipRegion.getProvince();
        }
        if (Objects.equals(country, "0")) {
            country = ipRegion.getCity();
        }
        return new BlogIpRegionVO(country, ipRegion.getProvince());
    }

}
