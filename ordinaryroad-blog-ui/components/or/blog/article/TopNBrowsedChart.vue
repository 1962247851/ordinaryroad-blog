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
  <v-card :loading="loading" flat outlined>
    <v-card-title>
      {{ $t('myStats.charts.topNBrowsedArticle') }}
      <v-btn
        icon
        small
        class="ms-2"
        :loading="loading"
        @click="getTopN"
      >
        <v-icon>
          mdi-refresh
        </v-icon>
      </v-btn>
    </v-card-title>
    <div v-if="!loading">
      <div
        v-if="options.series[0].data.length"
        ref="div"
        style="width: 100%; height: 400px"
      />
      <div v-else>
        <or-empty style="width: 100%; height: 400px" />
      </div>
    </div>
    <div v-else>
      <v-skeleton-loader tile height="200" type="image" />
      <v-skeleton-loader tile height="200" type="image" />
    </div>
  </v-card>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'OrBlogArticleTopNBrowsedChart',
  data: () => ({
    loading: true,
    topN: [],
    chart: undefined,
    options: {
      tooltip: {},
      xAxis: {
        data: []
      },
      yAxis: {},
      series: [
        {
          type: 'bar',
          data: []
        }
      ]
    }
  }),
  computed: {
    ...mapGetters('user', {
      userInfo: 'getUserInfo'
    })
  },
  created () {
    this.getTopN()
  },
  mounted () {
  },
  methods: {
    getTopN () {
      this.loading = true
      this.$apis.blog.article.getTopNBrowsed({ userId: this.userInfo.user.uuid })
        .then((data) => {
          this.options.xAxis.data = []
          this.options.series[0].data = []
          data.forEach((item) => {
            this.options.xAxis.data.push(item.title)
            this.options.series[0].data.push(item.browsed_count)
          })
          this.topN = data
          this.loading = false

          if (this.options.series[0].data.length) {
            this.$nextTick(() => {
              this.chart = this.$echarts.init(this.$refs.div)
              window.addEventListener('resize', this.chart.resize, true)
              this.chart.setOption(this.options)
            })
          }
        })
        .catch(() => {
          this.loading = false
        })
    }
  }
}
</script>

<style scoped>

</style>
