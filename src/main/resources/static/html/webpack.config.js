// webpack.config.js
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: ['./index.html'], // 你的入口文件，根据你的项目结构来
  //entry:['./models/tiny_face_detector_model-weights_manifest.json'],
  output: {
    path: path.resolve(__dirname, 'dist'), // 打包之后的文件存储位置
   // filename: 'index.htm', // 打包之后的文件名
  },
  plugins: [
    new HtmlWebpackPlugin({
        template: './index.html',  // 输入文件路径
        filename: 'index.html',    // 输出文件名
      })
  ],
  module: {
    rules: [
      {
        test: /\.html$/,
        use: ['html-loader']
      },
      // 其他规则
    ]
  }
};