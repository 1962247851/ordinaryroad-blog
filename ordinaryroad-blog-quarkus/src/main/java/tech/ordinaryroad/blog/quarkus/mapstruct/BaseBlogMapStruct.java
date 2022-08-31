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

import cn.hutool.core.util.StrUtil;
import tech.ordinaryroad.blog.quarkus.entity.BlogType;
import tech.ordinaryroad.blog.quarkus.entity.BlogUser;
import tech.ordinaryroad.blog.quarkus.service.BlogTypeService;
import tech.ordinaryroad.blog.quarkus.service.BlogUserService;
import tech.ordinaryroad.blog.quarkus.vo.BlogTypeVO;
import tech.ordinaryroad.blog.quarkus.vo.BlogUserVO;

import javax.enterprise.inject.spi.CDI;

/**
 * @author mjz
 * @date 2022/8/30
 */
public interface BaseBlogMapStruct {

    default BlogTypeVO string2TypeVO(String type) {
        if (StrUtil.isBlank(type)) {
            return null;
        }
        BlogTypeService typeService = CDI.current().select(BlogTypeService.class).get();
        BlogType blogType = typeService.findById(type);
        if (blogType.getDeleted()) {
            return null;
        }
        return BlogTypeMapStruct.INSTANCE.transfer(blogType);
    }

    default BlogUserVO string2UserVO(String createBy) {
        BlogUserService userService = CDI.current().select(BlogUserService.class).get();
        BlogUser user = userService.findById(createBy);
        return BlogUserMapStruct.INSTANCE.transfer(user);
    }

}
