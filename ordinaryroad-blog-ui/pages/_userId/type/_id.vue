<!--
  - MIT License
  -
  - Copyright (c) 2021 苗锦洲
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

<template>
  <base-material-card>
    <template #heading>
      <div
        class="text-h4 font-weight-light"
        v-text="`${type.name}${totalArticle?$t('parenthesesWithSpace',[totalArticle]):''}`"
      />
      <div class="category font-weight-thin">
        <or-link
          hide-icon
          :href="`/${type.creatorUid}`"
        >
          <span style="color: white">{{ type.createBy }}</span>
        </or-link>
        {{ $t('createAtWithSpace', [$dayjs(type.createdTime).format()]) }}
      </div>
    </template>
    <or-blog-article-list
      :total.sync="totalArticle"
      :create-by="type.creatorId"
      :type-id="type.uuid"
      auto-load-more
    />
  </base-material-card>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  asyncData ({
    route,
    $apis,
    store,
    error,
    redirect
  }) {
    // 判断分类是否存在
    const userId = route.params.userId || ''
    const id = route.params.id || ''
    if (id && id.trim() !== '') {
      return $apis.blog.type.findById(id)
        .then((data) => {
          if (!data) {
            error({ statusCode: 404, message: '分类不存在' })
          }
          if (data.creatorUid !== userId) {
            redirect(`/${userId}`)
          }
          return { type: data }
        })
        .catch(() => {
          error({ statusCode: 404, message: '分类不存在' })
        })
    } else {
      error({ statusCode: 404, message: '分类不存在' })
    }
  },
  data: () => ({
    type: null,
    totalArticle: null
  }),
  head () {
    return {
      title: this.type.name,
      titleTemplate: `%s - ${this.$t('appName')}`
    }
  },
  mounted () {
    if (this.type == null) {
      this.$dialog({
        persistent: true,
        content: this.$i18n.t('status.type.notFound'),
        confirmText: this.$i18n.t('retry'),
        cancelText: this.$i18n.t('back')
      }).then(({ isConfirm }) => {
        if (isConfirm) {
          this.$router.go(0)
        } else {
          this.$router.back()
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
