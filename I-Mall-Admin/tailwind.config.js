module.exports = {
  important: true,
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}'
  ],
  theme: {
    extend: {
      backgroundImage: {
        'login': "url('/src/assets/img/login_bg.jpg')",
      },
      width: {
        '112': '28rem'
      },
      height: {
        '112': '28rem'
      }
    }
  },
  plugins: []
}
