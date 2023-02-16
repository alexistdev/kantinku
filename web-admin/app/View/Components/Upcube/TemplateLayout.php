<?php

namespace App\View\Components\Upcube;

use Illuminate\View\Component;

class TemplateLayout extends Component
{
    public $title;

    public function __construct($title)
    {
        $this->title  =$title;
    }

    /**
     * Get the view / contents that represent the component.
     *
     * @return \Illuminate\Contracts\View\View|\Closure|string
     */
    public function render()
    {
        return view('components.upcube.template-layout');
    }
}
