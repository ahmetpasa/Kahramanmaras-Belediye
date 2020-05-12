class jsoup extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);

        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try{
                Document doc=Jsoup.connect("https://kahramanmaras.bel.tr/").get();
                Elements ess=doc.select("a[class=ms-news-swiper-a clearfix] href");
                Elements es=doc.select("div[id=ms-swiper-wrapper-discover]");
               s+= es.select("a[href]").get(1).text("href").absUrl("href");



            //    s+=es.get(0);




            } catch (Exception e) {
                s="fuck";
                e.printStackTrace();
            }
            return s;
        }
    }