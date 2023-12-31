import Header from '@/app/_components/header/Header';
import Carousel from './_components/carousel/Carousel';

export default async function Home() {
  return (
    <main className="h-[500px]">
      <Header />
      <div className="mt-[80px]">
        <Carousel />
      </div>
    </main>
  );
}
