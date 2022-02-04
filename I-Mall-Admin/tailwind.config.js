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
      }
    }
  },
  plugins: []
}
