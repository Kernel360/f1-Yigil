module.exports = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      backgroundImage: {},
      colors: {
        main: '#60A5FA',
        violet: '#A78BFA',
      },
      keyframes: {
        appear: {
          '0%': { opacity: '30', scale: '0.8' },
          '100%': { opacity: '100', scale: '1' },
        },
      },
      animation: {
        appear: 'appear .5s ease-in-out',
        bounceOne: 'bounce 1.2s ease-in-out',
      },
    },
  },

  plugins: [],
};
